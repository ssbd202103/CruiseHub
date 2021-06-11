package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations.CruiseReservationDto;

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
        return new CruiseReservationDto(reservation.getClient().getAccount().getLogin(), reservation.getNumberOfSeats(), reservation.getPrice(),
                reservation.getCruise().getCruisesGroup().getName());
    }
}
