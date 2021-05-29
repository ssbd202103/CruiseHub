package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
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
import java.util.List;
import java.util.UUID;

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
        long id = cruiseFacadeMow.findByUUID(cruise_uuid).getId();
        List<Reservation> res = reservationFacadeMow.findCruiseReservations(id);
        return res;

    }

    @RolesAllowed("getWorkerCruiseReservations")
    @Override
    public List<Reservation> getWorkerCruiseReservations(UUID cruise_uuid) throws BaseAppException {
        long id = cruiseFacadeMow.findByUUID(cruise_uuid).getId();
        List<Reservation> res = reservationFacadeMow.findWorkerCruiseReservations(id);
        return res;
    }

    @RolesAllowed("removeClientReservation")
    @Override
    public void removeClientReservation(long reservationVersion, UUID reservationUuid, String clientLogin) throws BaseAppException {
        // todo finish implementation
        Reservation reservation = reservationFacadeMow.findReservationByUuidAndLogin(UUID.randomUUID(), clientLogin);
    }

    @RolesAllowed("createReservation")
    @Override
    public void createReservation(long version, UUID cruiseUUID, long numberOfSeats, String login) throws BaseAppException {
        Cruise cruise = cruiseFacadeMow.findByUUID(cruiseUUID);
        Account acc = accountFacadeMow.findByLogin(login);
        if (numberOfSeats > getAvailableSeats(cruiseUUID)) {
            // todo throw an exception
        }
//        Reservation reservation = new Reservation(numberOfSeats, cruise, acc);
//        reservation.setPrice(cruise.getCruisesGroup().getPrice() * numberOfSeats);

//        reservationFacadeMow.create(reservation);
    }

    @RolesAllowed("cancelReservation")
    @Override
    public void cancelReservation(long reservationVersion, UUID cruiseUUID, String login) throws BaseAppException {
        Cruise cruise = cruiseFacadeMow.findByUUID(cruiseUUID);
        // TODO implement
    }

    private long getAvailableSeats(UUID cruiseUUID) throws BaseAppException {
        List<Reservation> reservations = getCruiseReservations(cruiseUUID);
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
    public Account getCurrentUser() throws BaseAppException {
        return accountFacadeMow.findByLogin(context.getUserPrincipal().getName());
    }
}
