package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;

import javax.ejb.Local;
import java.util.List;
import java.util.UUID;

/**
 * Klasa która zarządza logiką biznesową rezerwacji
 */
@Local
public interface ReservationManagerLocal {
    /**
     * zwaraca listę rezerwacji dla podanej wycieczki
     *
     * @param cruise_uuid uuid wycieczki dla której chcemy znaleść rezerwacje
     * @return Lista rezerwacji dla podanej wycieczki
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    List<Reservation> getCruiseReservations(UUID cruise_uuid) throws BaseAppException;

    /**
     * Zwraca liste rezerwacji dla danej wycieczki zalogowanego buisnse-workera
     *
     * @param cruise_uuid
     * @return
     * @throws BaseAppException
     */
    List<Reservation> getWorkerCruiseReservations(UUID cruise_uuid) throws BaseAppException;

    /**
     * Usuwa wycieczke klienta podanego w obiekci dto
     *
     * @param reservationVersion
     * @param reservationUuid
     * @param clientLogin
     * @throws BaseAppException
     */
    void removeClientReservation(long reservationVersion, UUID reservationUuid, String clientLogin) throws BaseAppException;

    /**
     * Rezerwuje wycieczke dla klienta
     * @param version Wersja wycieczki
     * @param cruiseUUID UUID wycieczki
     * @param numberOfSeats Liczba rezerwowanych miejsc
     * @param login Login użytkownika
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    void createReservation(long version, UUID cruiseUUID, long numberOfSeats, String login) throws BaseAppException;
}
