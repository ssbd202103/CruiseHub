package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.*;

import javax.ejb.Local;
import java.util.List;
import java.util.UUID;

/**
 * Interfejs który zajmuje się gromadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z rezerwacją, oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Local
public interface ReservationEndpointLocal {
    /**
     * Pobiera wszystkie rezerwacje dla danej wycieczki
     *
     * @param cruise_uuid identyfikator wycieczki
     * @return lista rezerwacji
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    List<CruiseReservationDto> viewCruiseReservations(UUID cruise_uuid) throws BaseAppException;

    /**
     * Pobiera wszytskie rezerwacje dla danej wycieczki która nalezy do biznes_worker'a
     *
     * @param cruise_uuid
     * @return
     * @throws BaseAppException
     */
    List<CruiseReservationDto> viewWorkerCruiseReservations(UUID cruise_uuid) throws BaseAppException;

    /**
     * Usuwa wycieczke klienta podanego w obiekci dto
     *
     * @param removeClientReservationDto
     * @return
     * @throws BaseAppException
     */
    void removeClientReservation(RemoveClientReservationDto removeClientReservationDto) throws BaseAppException;

    /**
     * Rezerwuje wycieczke dla podanego klienta
     * @param createReservationDto Informacja o kliencie oraz wycieczne
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    void createReservation(CreateReservationDto createReservationDto) throws BaseAppException;

    /**
     * Metoda sluzaca do anulowania zarezerwowanej wycieczki
     * @param cancelReservationDTO Informacja o kliencie oraz wycieczce
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    void cancelReservation(CancelReservationDTO cancelReservationDTO) throws BaseAppException;


    /**
     * Metoda zwracająca listę rezerwacji obecnie zalogowanego klienta
     * @return lista rezerwacji klienta
     * @throws BaseAppException bazowy wyjątek aplikacji
     */
    List<CruiseReservationDto> viewSelfCruiseReservations() throws BaseAppException;
}

