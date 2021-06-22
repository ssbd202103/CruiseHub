package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.CruiseManagerException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.NoSeatsAvailableException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.converters.AccountMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.ReservationFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.IntegrityUtils.checkForOptimisticLock;

/**
 * Klasa która zarządza logiką biznesową rezerwacji
 */

@Stateful
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(TrackingInterceptor.class)
public class ReservationManager extends BaseManagerMow implements ReservationManagerLocal {

    @Inject
    private ReservationFacadeMow reservationFacadeMow;

    @Inject
    private CruiseFacadeMow cruiseFacadeMow;

    @Override
    @RolesAllowed("viewCruiseReservations")
    public List<Reservation> getCruiseReservations(UUID cruise_uuid) throws BaseAppException {
        Cruise cruise = cruiseFacadeMow.findByUUID(cruise_uuid);
        return reservationFacadeMow.findCruiseReservations(cruise);
    }

    @RolesAllowed("getWorkerCruiseReservations")
    @Override
    public List<Reservation> getWorkerCruiseReservations(UUID cruise_uuid) throws BaseAppException {
        Cruise cruise = cruiseFacadeMow.findByUUID(cruise_uuid);
        Account account = getCurrentUser();

        BusinessWorker businessWorker = (BusinessWorker) AccountMapper.getAccessLevel(account, AccessLevelType.BUSINESS_WORKER);

        if (cruise.getCruisesGroup().getCompany().getNIP() != businessWorker.getCompany().getNIP()) {
            throw new CruiseManagerException(NOT_YOURS_CRUISE);
        }

        return reservationFacadeMow.findCruiseReservations(cruise);
    }

    @RolesAllowed("removeClientReservation")
    @Override
    public void removeClientReservation(UUID reservationUuid, String clientLogin) throws BaseAppException {
        Reservation reservation = reservationFacadeMow.findReservationByUuidAndLogin(reservationUuid, clientLogin);
        reservationFacadeMow.remove(reservation);
    }


    @RolesAllowed("createReservation")
    @Override
    public void createReservation(long version, UUID cruiseUUID, long numberOfSeats) throws BaseAppException {
        Cruise cruise = cruiseFacadeMow.findByUUID(cruiseUUID);
        checkForOptimisticLock(cruise, version);

        Account account = getCurrentUser();
        Client client = (Client) AccountMapper.getAccessLevel(account, AccessLevelType.CLIENT);

        if (numberOfSeats > getAvailableSeats(cruiseUUID)) {
            throw new NoSeatsAvailableException(NO_SEATS_AVAILABLE);
        }

        Reservation reservation = new Reservation(numberOfSeats, cruise, cruise.getCruisesGroup().getPrice() * numberOfSeats, client);

        setCreatedMetadata(account, reservation);
        reservationFacadeMow.create(reservation);
    }

    @RolesAllowed("cancelReservation")
    @Override
    public void cancelReservation(UUID reservationUUID) throws BaseAppException {
        String login = getCurrentUser().getLogin();
        Reservation reservation = reservationFacadeMow.findReservationByUuidAndLogin(reservationUUID, login);

        if (reservation.getCruise().getStartDate().isBefore(LocalDateTime.now())) {
            throw new FacadeException(CANNOT_CANCEL_STARTED_CRUISE);
        }

        reservationFacadeMow.remove(reservation);
    }

    private long getAvailableSeats(UUID cruiseUUID) throws BaseAppException {
        Cruise cruise = cruiseFacadeMow.findByUUID(cruiseUUID);
        List<Reservation> reservations = reservationFacadeMow.findCruiseReservations(cruise);

        long reservedSeats = reservations.stream().mapToLong(Reservation::getNumberOfSeats).sum();
        long allSeats = cruise.getCruisesGroup().getNumberOfSeats();

        return allSeats - reservedSeats;
    }

    @RolesAllowed("viewSelfReservations")
    @Override
    public List<Reservation> getClientReservations() throws BaseAppException {
        Account account = getCurrentUser();
        return reservationFacadeMow.findReservationByLogin(account.getLogin());
    }

}
