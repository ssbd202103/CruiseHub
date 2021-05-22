package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.ReservationFacadeMow;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Klasa która zarządza logiką biznesową rezerwacji
 */

@Stateful
public class ReservationManager implements ReservationManagerLocal{

    @Inject
    private ReservationFacadeMow reservationFacadeMow;

    @Inject
    private CruiseFacade cruiseFacade;

    @Override
    public List<Reservation> getCruiseReservations(UUID cruise_uuid) throws BaseAppException {
        long id= cruiseFacade.findByUUID(cruise_uuid).getId();
        List<Reservation> res = reservationFacadeMow.findCruiseReservations(id);
        return res;

    }
    @Override
    public List<Reservation> getWorkerCruiseReservations(UUID cruise_uuid) throws BaseAppException {
        long id= cruiseFacade.findByUUID(cruise_uuid).getId();
        List<Reservation> res = reservationFacadeMow.findWorkerCruiseReservations(id);
        return res;

    }
}
