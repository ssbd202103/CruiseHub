package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.ReservationFacadeMow;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa która zarządza logiką biznesową rezerwacji
 */

@Stateful
public class ReservationManager implements ReservationManagerLocal{

    @EJB
    private ReservationFacadeMow reservationFacadeMow;


    @Override
    public List<Reservation> getCruiseReservations(long cruise_id) throws BaseAppException {
        List<Reservation> res = reservationFacadeMow.findCruiseReservations(cruise_id);
        return res;

    }
    @Override
    public List<Reservation> getWorkerCruiseReservations(long cruise_id) throws BaseAppException {
        List<Reservation> res = reservationFacadeMow.findWorkerCruiseReservations(cruise_id);
        return res;

    }
}
