package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints.BaseEndpoint;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CancelReservationDTO;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CreateReservationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CruiseReservationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RemoveClientReservationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.ReservationMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.ReservationManagerLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Klasa która zajmuje się growadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z rezerwacją wycieczek, oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(TrackingInterceptor.class)
public class ReservationEndpoint extends BaseEndpoint implements ReservationEndpointLocal {

    @Inject
    private ReservationManagerLocal reservationManager;


    @Override
    @RolesAllowed("viewCruiseReservations")
    public List<CruiseReservationDto> viewCruiseReservations(UUID cruise_uuid) throws BaseAppException {
        List<CruiseReservationDto> res = new ArrayList<>();
        for (Reservation reservation : reservationManager.getCruiseReservations(cruise_uuid)) {
            res.add(ReservationMapper.toReservationDto(reservation));
        }
        return res;
    }

    //TODO how to get current user ?
    @RolesAllowed("getWorkerCruiseReservations")
    @Override
    public List<CruiseReservationDto> viewWorkerCruiseReservations(UUID cruise_uuid) throws BaseAppException {
        List<CruiseReservationDto> res = new ArrayList<>();
        for (Reservation reservation : reservationManager.getCruiseReservations(cruise_uuid)) {
            res.add(ReservationMapper.toReservationDto(reservation));
        }
        return res;
    }

    @RolesAllowed("removeClientReservation")
    @Override
    public void removeClientReservation(RemoveClientReservationDto removeClientReservationDto) throws BaseAppException {
        this.reservationManager.removeClientReservation(removeClientReservationDto.getReservationVersion(), removeClientReservationDto.getReservationUuid(), removeClientReservationDto.getClientLogin());
        // todo implement
    }

    @RolesAllowed("createReservation")
    @Override
    public void createReservation(CreateReservationDto crDto) throws BaseAppException {
        this.reservationManager.createReservation(crDto.getCruiseVersion(), crDto.getCruiseUuid(), crDto.getNumberOfSeats(), crDto.getClientLogin());
    }

    @RolesAllowed("cancelReservation")
    @Override
    public void cancelReservation(CancelReservationDTO crDto) throws BaseAppException {
        this.reservationManager.cancelReservation(crDto.getReservationVersion(), crDto.getCruiseUuid(), crDto.getClientLogin());
    }

    @RolesAllowed("viewSelfReservations")
    @Override
    public List<CruiseReservationDto> viewSelfCruiseReservations() throws BaseAppException {
        List<CruiseReservationDto> res = new ArrayList<>();
        // todo finish implementation
        return res;
    }
}
