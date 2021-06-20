package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.ControllerException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations.CruiseReservationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations.RemoveClientReservationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations.SelfReservationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.ReservationEndpointLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.security.ETagFilterBinding;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.utils.TransactionRepeater.tryAndRepeat;

@Path("/reservation")
public class ReservationController {
    @Inject
    private ReservationEndpointLocal reservationEndpoint;

    /**
     * Pobiera informacje o rezerwacjach dla danej wycieczki
     *
     * @return Lista rezerwacji
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @GET
    @Path("/reservations-for-cruise/{cruiseUUID}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CruiseReservationDto> getCruisesForReservations(@PathParam("cruiseUUID") UUID cruiseUUID) throws BaseAppException {
        return tryAndRepeat(reservationEndpoint, () -> reservationEndpoint.viewCruiseReservations(cruiseUUID));
    }

    /**
     * Pobiera informacje o rezerwacjach dla danej wycieczki zalogowanego buisnse-workera
     *
     * @return Lista rezerwacji
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @GET
    @Path("/reservations-for-worker-cruise/{cruiseUUID}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CruiseReservationDto> getWorkerCruisesForReservations(@PathParam("cruiseUUID") UUID cruiseUUID) throws BaseAppException {
        return tryAndRepeat(reservationEndpoint, () -> reservationEndpoint.viewWorkerCruiseReservations(cruiseUUID));
    }

    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/{clientLogin}/{reservationUuid}")
    public void removeReservation(@PathParam("clientLogin") String clientLogin,
                                  @PathParam("reservationUuid") UUID reservationUuid
                                  ) throws BaseAppException {
        reservationEndpoint.removeClientReservation(new RemoveClientReservationDto(reservationUuid.toString(), clientLogin));
    }

    /**
     * Metoda odpowiedzialna za zwrócenie listy rezerwacji obecnie zalogowanego klienta
     * @return Lista rezerwacji
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @GET
    @Path("/self-reservations")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SelfReservationDto> getSelfReservations() throws BaseAppException {
        return tryAndRepeat(reservationEndpoint, () -> reservationEndpoint.viewSelfCruiseReservations());
    }
}
