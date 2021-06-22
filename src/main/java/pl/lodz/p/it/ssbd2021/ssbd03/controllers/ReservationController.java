package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.common.dto.MetadataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.ControllerException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.MapperException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations.CruiseReservationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations.RemoveClientReservationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations.SelfReservationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.ReservationEndpointLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.security.ETagFilterBinding;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;
import static pl.lodz.p.it.ssbd2021.ssbd03.utils.TransactionRepeater.tryAndRepeat;

@Path("/reservation")
@RequestScoped
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
    public List<CruiseReservationDto> getCruisesForReservations(@PathParam("cruiseUUID") UUID cruiseUUID) throws BaseAppException { //todo change UUID to String and handle parsing exception
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
    public List<CruiseReservationDto> getWorkerCruisesForReservations(@PathParam("cruiseUUID") UUID cruiseUUID) throws BaseAppException { //todo change UUID to String and handle parsing exception
        return tryAndRepeat(reservationEndpoint, () -> reservationEndpoint.viewWorkerCruiseReservations(cruiseUUID));
    }

    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/{clientLogin}/{reservationUuid}")
    public void removeReservation(@PathParam("clientLogin") String clientLogin,
                                  @PathParam("reservationUuid") UUID reservationUuid
                                  ) throws BaseAppException {
        reservationEndpoint.removeClientReservation(new RemoveClientReservationDto(reservationUuid.toString(), clientLogin)); //todo change UUID to String and handle parsing exception
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

    /**
     * Pobiera metadane rezerwacji
     *
     * @param uuid UUID rezerwacji wybranej do metadanych
     * @return Reprezentacja DTO metadanych
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @GET
    @Path("/metadata/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public MetadataDto getReservationMetadata(@PathParam("uuid") String uuid) throws BaseAppException {
        try{
            UUID convertedUUID = UUID.fromString(uuid);
            return tryAndRepeat(reservationEndpoint, () -> reservationEndpoint.getReservationMetadata(convertedUUID));
        }catch (IllegalArgumentException e) {
            throw new MapperException(MAPPER_UUID_PARSE);
        }
    }

}
