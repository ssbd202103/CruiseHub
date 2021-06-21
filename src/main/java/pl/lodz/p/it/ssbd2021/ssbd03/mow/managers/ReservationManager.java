package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

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
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.CruiseManagerException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.NoSeatsAvailableException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.AccountFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.ReservationFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

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
import static pl.lodz.p.it.ssbd2021.ssbd03.common.IntegrityUtils.checkForOptimisticLock;

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

    @Inject
    private AccountFacadeMow accountFacade;

    @Context
    private SecurityContext securityContext;

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
        Account account = accountFacade.findByLogin(securityContext.getUserPrincipal().getName());
        try {
            Optional<AccessLevel> accessLevelBusinessWorker = account.getAccessLevels().stream().filter(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.BUSINESS_WORKER).findFirst();
            if (accessLevelBusinessWorker.isEmpty()) {
                throw new CruiseManagerException(CANNOT_FIND_ACCESS_LEVEL);
            }
            BusinessWorker businessWorker = (BusinessWorker) accessLevelBusinessWorker.get();
            if (cruise.getCruisesGroup().getCompany().getNIP() != businessWorker.getCompany().getNIP()) {
                throw new CruiseManagerException(NOT_YOURS_CRUISE);
            }
        } catch (ClassCastException e) {
            throw new CruiseManagerException(CANNOT_FIND_ACCESS_LEVEL);
        }
        List<Reservation> reservations = reservationFacadeMow.findCruiseReservations(cruise);
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
    public void createReservation(long version, UUID cruiseUUID, long numberOfSeats) throws BaseAppException { //todo refactor this
        Cruise cruise = cruiseFacadeMow.findByUUID(cruiseUUID);
        checkForOptimisticLock(cruise, version);

        Account acc = accountFacadeMow.findByLogin(context.getUserPrincipal().getName());
        Client client = (Client) acc.getAccessLevels().stream().filter(accessLevel ->
                accessLevel.getAccessLevelType().equals(AccessLevelType.CLIENT)).collect(Collectors.toList()).get(0);

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
    public void cancelReservation(UUID reservationUUID) throws BaseAppException {
        String login = context.getUserPrincipal().getName();
        Reservation reservation = reservationFacadeMow.findReservationByUuidAndLogin(reservationUUID, login);
        if (reservation.getCruise().getStartDate().isBefore(LocalDateTime.now())) {
            throw new FacadeException(CANNOT_CANCEL_STARTED_CRUISE);
        }
        reservation.setAlteredBy(getCurrentUser());
        reservation.setAlterType(accountFacadeMow.getAlterTypeWrapperByAlterType(AlterType.DELETE));
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
}
