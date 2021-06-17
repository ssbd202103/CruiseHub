package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.BusinessWorkerWithCompanyDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.converters.AccountMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.AccountFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.ReservationFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

/**
 * Klasa która zarządza logiką biznesową rezerwacji
 */

@Stateful
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(TrackingInterceptor.class)
public class ReservationManager implements ReservationManagerLocal {

    @Inject
    private ReservationFacadeMow reservationFacadeMow;

    @Inject
    private AccountFacadeMow accountFacadeMow;

    @Inject
    private CruiseFacadeMow cruiseFacadeMow;

    @Context
    private SecurityContext context;


    @Override
    @RolesAllowed("viewCruiseReservations")
    public List<Reservation> getCruiseReservations(UUID cruise_uuid) throws BaseAppException {
        Cruise cruise = cruiseFacadeMow.findByUUID(cruise_uuid);
        List<Reservation> res = reservationFacadeMow.findCruiseReservations(cruise);
        return res;

    }

    @RolesAllowed("getWorkerCruiseReservations")
    @Override
    public List<Reservation> getWorkerCruiseReservations(UUID cruise_uuid) throws BaseAppException {
        Cruise cruise = cruiseFacadeMow.findByUUID(cruise_uuid);
        Account account = getCurrentUser();
        BusinessWorkerWithCompanyDto businessWorkerDto = getBusinessWorkerByLogin(account.getLogin());
        List<Reservation> reservations = reservationFacadeMow.findCruiseReservations(cruise);
        if (!businessWorkerDto.getCompanyName().equals(cruise.getCruisesGroup().getCompany().getName())) {
            throw new ReservationManagerException(NOT_YOURS_CRUISE);
        }
        return reservations;
    }

    @RolesAllowed("removeClientReservation")
    @Override
    public void removeClientReservation(UUID reservationUuid, String clientLogin) throws BaseAppException {
        Reservation reservation = reservationFacadeMow.findReservationByUuidAndLogin(reservationUuid, clientLogin);

        reservation.setAlteredBy(getCurrentUser());
        reservation.setAlterType(accountFacadeMow.getAlterTypeWrapperByAlterType(AlterType.DELETE));
        reservationFacadeMow.remove(reservation);
    }


    @RolesAllowed("createReservation")
    @Override
    public void createReservation(long version, UUID cruiseUUID, long numberOfSeats) throws BaseAppException {
        Cruise cruise = cruiseFacadeMow.findByUUID(cruiseUUID);
        Account acc = accountFacadeMow.findByLogin(context.getUserPrincipal().getName());
        Client client = (Client) acc.getAccessLevels().stream().filter(accessLevel ->
                accessLevel.getAccessLevelType().equals(AccessLevelType.CLIENT)).collect(Collectors.toList()).get(0);

        if (cruise.getVersion() != version) {
            throw FacadeException.optimisticLock();
        }
        if (numberOfSeats > getAvailableSeats(cruiseUUID)) {
            throw new NoSeatsAvailableException(I18n.NO_SEATS_AVAILABLE);
        }

        Reservation reservation = new Reservation(numberOfSeats, cruise, client);
        reservation.setPrice(cruise.getCruisesGroup().getPrice() * numberOfSeats);
        reservation.setAlterType(accountFacadeMow.getAlterTypeWrapperByAlterType(AlterType.INSERT));
        reservation.setCreatedBy(acc);
        reservation.setAlteredBy(acc);
        reservation.setLastAlterDateTime(LocalDateTime.now());
        reservation.setUuid(UUID.randomUUID());
        reservationFacadeMow.create(reservation);
    }

    @RolesAllowed("cancelReservation")
    @Override
    public void cancelReservation(long reservationVersion, UUID reservationUUID) throws BaseAppException {
        Reservation reservation = reservationFacadeMow.findReservationByUuidAndLogin(reservationUUID, context.getUserPrincipal().getName());
        if (reservation.getVersion() != reservationVersion) {
            throw FacadeException.optimisticLock();
        }
        reservationFacadeMow.remove(reservation);
    }

    private long getAvailableSeats(UUID cruiseUUID) throws BaseAppException {
        List<Reservation> reservations;
        long id = cruiseFacadeMow.findByUUID(cruiseUUID).getId();
        reservations = reservationFacadeMow.findCruiseReservationsOrReturnEmptyList(id);
        long takenSeats = 0L;
        for (Reservation res : reservations) {
            takenSeats += res.getNumberOfSeats();
        }
        long allSeats = cruiseFacadeMow.findByUUID(cruiseUUID).getCruisesGroup().getNumberOfSeats();

        return allSeats - takenSeats;
    }

    @RolesAllowed("viewSelfReservations")
    @Override
    public List<Reservation> getClientReservations() throws BaseAppException {
        Account account = getCurrentUser();
        return reservationFacadeMow.findReservationByLogin(account.getLogin());
    }

    @RolesAllowed("authenticatedUser")
    private Account getCurrentUser() throws BaseAppException {
        return accountFacadeMow.findByLogin(context.getUserPrincipal().getName());
    }

    private static AccessLevel getAccessLevel(Account from, AccessLevelType target) throws BaseAppException {
        Optional<AccessLevel> optionalAccessLevel = from.getAccessLevels().stream()
                .filter(accessLevel -> accessLevel.getAccessLevelType().equals(target)).findAny();

        return optionalAccessLevel.orElseThrow(() -> new AccountManagerException(ACCESS_LEVEL_DOES_NOT_EXIST_ERROR));
    }

    private AccessLevel getAccountAccessLevel(String login, AccessLevelType accessLevelType) throws BaseAppException {
        Account account = accountFacadeMow.findByLogin(login);
        return getAccessLevel(account, accessLevelType);
    }

    @RolesAllowed("getWorkerCruiseReservations")
    private BusinessWorkerWithCompanyDto getBusinessWorkerByLogin(String login) throws BaseAppException {
        return AccountMapper.toBusinessWorkerWithCompanyDto((BusinessWorker) getAccountAccessLevel(login, AccessLevelType.BUSINESS_WORKER));
    }

}
