package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints.BaseEndpoint;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.MapperException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations.*;
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

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CRUISE_MAPPER_UUID_PARSE;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.RESERVATION_MAPPER_UUID_PARSE;

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
        for (Reservation reservation : reservationManager.getWorkerCruiseReservations(cruise_uuid)) {
            res.add(ReservationMapper.toReservationDto(reservation));
        }
        return res;
    }

    @RolesAllowed("removeClientReservation")
    @Override
    public void removeClientReservation(RemoveClientReservationDto removeClientReservationDto) throws BaseAppException {
        try {
            this.reservationManager.removeClientReservation(UUID.fromString(removeClientReservationDto.getReservationUuid()), removeClientReservationDto.getClientLogin());
        } catch (IllegalArgumentException e) {
            throw new MapperException(RESERVATION_MAPPER_UUID_PARSE);
        }
    }

    @RolesAllowed("createReservation")
    @Override
    public void createReservation(CreateReservationDto crDto) throws BaseAppException {
        this.reservationManager.createReservation(crDto.getCruiseVersion(), crDto.getCruiseUuid(), crDto.getNumberOfSeats());
    }

    @RolesAllowed("cancelReservation")
    @Override
    public void cancelReservation(UUID reservationUUID) throws BaseAppException {
        this.reservationManager.cancelReservation(reservationUUID);
    }

    @RolesAllowed("viewSelfReservations")
    @Override
    public List<SelfReservationDto> viewSelfCruiseReservations() throws BaseAppException {
        List<SelfReservationDto> res = new ArrayList<>();
        for (Reservation reservation : reservationManager.getClientReservations()) {
            res.add(ReservationMapper.toSelfReservationDto(reservation));
        }
        return res;
    }
}
