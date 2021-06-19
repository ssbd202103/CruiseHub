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
     * @param cruise_uuid UUID wycieczki
     * @return Listra rezerwacji dla danej wycieczki
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    List<Reservation> getWorkerCruiseReservations(UUID cruise_uuid) throws BaseAppException;

    /**
     * Usuwa wycieczke klienta podanego w obiekci dto
     *
     * @param reservationUuid UUID rezerwacji
     * @param clientLogin Login klienta
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    void removeClientReservation(UUID reservationUuid, String clientLogin) throws BaseAppException;

    /**
     * Rezerwuje wycieczke dla klienta
     * @param version Wersja wycieczki
     * @param cruiseUUID UUID wycieczki
     * @param numberOfSeats Liczba rezerwowanych miejsc
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    void createReservation(long version, UUID cruiseUUID, long numberOfSeats) throws BaseAppException;

    /**
     * Anuluje wycieczke uzytkownika
     * @param reservationUUID UUID rezerwacji
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    void cancelReservation(UUID reservationUUID) throws BaseAppException;

    /**
     * Zwraca listę rezerwacji obecnie zalogowanego klienta
     * @return listę rezerwacji
     * @throws BaseAppException bazowy wyjątek aplikacji
     */
    List<Reservation> getClientReservations() throws BaseAppException;
}
