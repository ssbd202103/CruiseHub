package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CruiseReservationDto;

import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs który zajmuje się gromadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z rezerwacją, oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Local
public interface ReservationEndpointLocal {
    /**
     * Pobiera wszystkie rezerwacje dla danej wycieczki
     * @param cruise_id identyfikator wycieczki
     * @return  lista rezerwacji
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    List<CruiseReservationDto> viewCruiseReservations(long cruise_id) throws BaseAppException;
}
