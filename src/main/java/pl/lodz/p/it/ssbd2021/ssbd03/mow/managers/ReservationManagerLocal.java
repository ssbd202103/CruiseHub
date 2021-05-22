package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;

import javax.ejb.Local;
import java.util.List;

/**
 * Klasa która zarządza logiką biznesową rezerwacji
 */
@Local
public interface ReservationManagerLocal {
    /**
     * zwaraca listę rezerwacji dla podanej wycieczki
     * @param cruise_id id wycieczki dla której chcemy znaleść rezerwacje
     * @return Lista rezerwacji dla podanej wycieczki
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    List<Reservation> getCruiseReservations(long cruise_id) throws BaseAppException;

    /**
     * Zwraca liste rezerwacji dla danej wycieczki zalogowanego buisnse-workera
     *
     * @param cruise_id
     * @return
     * @throws BaseAppException
     */
    List<Reservation> getWorkerCruiseReservations(long cruise_id) throws BaseAppException;
}
