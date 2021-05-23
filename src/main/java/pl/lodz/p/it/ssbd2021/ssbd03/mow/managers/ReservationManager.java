package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.ReservationFacadeMow;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

/**
 * Klasa która zarządza logiką biznesową rezerwacji
 */

@Stateful
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ReservationManager implements ReservationManagerLocal {

    @Inject
    private ReservationFacadeMow reservationFacadeMow;

    @Inject
    private CruiseFacadeMow cruiseFacadeMow;

    @Override
    public List<Reservation> getCruiseReservations(UUID cruise_uuid) throws BaseAppException {
        long id= cruiseFacadeMow.findByUUID(cruise_uuid).getId();
        List<Reservation> res = reservationFacadeMow.findCruiseReservations(id);
        return res;

    }
    @Override
    public List<Reservation> getWorkerCruiseReservations(UUID cruise_uuid) throws BaseAppException {
        long id= cruiseFacadeMow.findByUUID(cruise_uuid).getId();
        List<Reservation> res = reservationFacadeMow.findWorkerCruiseReservations(id);
        return res;

    }

    @RolesAllowed("removeClientReservation")
    @Override
    public void removeClientReservation(long reservationVersion, UUID reservationUuid, String clientLogin) throws BaseAppException {
        // todo finish implementation
        Reservation reservation = reservationFacadeMow.findReservationByUuidAndLogin(UUID.randomUUID(), clientLogin);
    }
}
