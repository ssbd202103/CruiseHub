package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Attraction;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.AttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations.CruiseReservationDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa która zajmuje się mapowaniem obiektów klas dto na obiekty klas modelu
 */
public class ReservationMapper {
    private ReservationMapper() {
    }

    /**
     * Mapuuje obiekt kilasy Reservaton na dto CruiseReservationDto
     *
     * @param reservation rezerwacja podawana konwersji
     * @return obiekt dto klasy CruiseReservationDto
     */
    public static CruiseReservationDto toReservationDto(Reservation reservation) {
        List<String> attractions = new ArrayList<>();
        for( Attraction att :reservation.getAttractions()){
            attractions.add(att.getName());
        }
        return new CruiseReservationDto(reservation.getUuid(),reservation.getClient().getAccount().getLogin(), reservation.getNumberOfSeats(), reservation.getPrice(),
                reservation.getCruise().getCruisesGroup().getName(),attractions);
    }
}
