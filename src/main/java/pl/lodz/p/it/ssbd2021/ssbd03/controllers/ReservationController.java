package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations.CruiseReservationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.ReservationEndpointLocal;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

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
        return tryAndRepeat(() -> reservationEndpoint.viewCruiseReservations(cruiseUUID));
    }

    /**
     * Pobiera informacje o rezerwacjach dla danej wycieczki
     *
     * @return Lista rezerwacji
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @GET
    @Path("/reservations-for-worker-cruise/{cruiseUUID}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CruiseReservationDto> getWorkerCruisesForReservations(@PathParam("cruiseUUID") UUID cruiseUUID) throws BaseAppException {
        return tryAndRepeat(() -> reservationEndpoint.viewWorkerCruiseReservations(cruiseUUID));
    }
}
