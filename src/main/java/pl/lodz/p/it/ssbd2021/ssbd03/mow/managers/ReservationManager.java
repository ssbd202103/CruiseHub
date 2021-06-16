package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
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
import java.util.UUID;
import java.util.stream.Collectors;

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
        Cruise cruise  = cruiseFacadeMow.findByUUID(cruise_uuid);
        List<Reservation> res = reservationFacadeMow.findCruiseReservations(cruise);
        return res;

    }

    @RolesAllowed("getWorkerCruiseReservations")
    @Override
    public List<Reservation> getWorkerCruiseReservations(UUID cruise_uuid) throws BaseAppException {
        Cruise cruise  = cruiseFacadeMow.findByUUID(cruise_uuid);
        List<Reservation> res = reservationFacadeMow.findCruiseReservations(cruise);
        return res;
/*

        long id = cruiseFacadeMow.findByUUID(cruise_uuid).getId();
        List<Reservation> res = reservationFacadeMow.findWorkerCruiseReservations(id);
        return res;*/
    }

    @RolesAllowed("removeClientReservation")
    @Override
    public void removeClientReservation(long reservationVersion, UUID reservationUuid, String clientLogin) throws BaseAppException {
        // todo finish implementation
        Reservation reservation = reservationFacadeMow.findReservationByUuidAndLogin(UUID.randomUUID(), clientLogin);
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
}
