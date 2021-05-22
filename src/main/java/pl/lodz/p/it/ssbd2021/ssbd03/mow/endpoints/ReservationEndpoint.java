package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CruiseReservationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.ReservationMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.ReservationManagerLocal;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa która zajmuje się growadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z rezerwacją wycieczek, oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Stateful
public class ReservationEndpoint implements ReservationEndpointLocal{

    @EJB
    private ReservationManagerLocal reservationManager;


    @Override
    public List<CruiseReservationDto> viewCruiseReservations(long cruise_id) throws BaseAppException {
        List<CruiseReservationDto> res = new ArrayList<>();
        for (Reservation reservation: reservationManager.getCruiseReservations(cruise_id)){
            res.add(ReservationMapper.toReservationDto(reservation));
        }
        return res;
    }

    //TODO how to get current user ?
    @Override
    public List<CruiseReservationDto> viewWorkerCruiseReservations(long cruise_id) throws BaseAppException {
        List<CruiseReservationDto> res = new ArrayList<>();
        for (Reservation reservation: reservationManager.getCruiseReservations(cruise_id)){
            res.add(ReservationMapper.toReservationDto(reservation));
        }
        return res;
    }

}
