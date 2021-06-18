package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.ControllerException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.MapperException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.*;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.ControllerException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations.CancelReservationDTO;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations.CreateReservationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.CruiseEndpointLocal;
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
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.utils.TransactionRepeater.tryAndRepeat;

/**
 * Klasa udostepniajaca API do wykonywania operacji na wycieczkach
 */
@Path("/cruise")
@RequestScoped
public class CruiseController {

    @Inject
    ReservationEndpointLocal reservationEndpoint;
    @Inject
    private CruiseEndpointLocal cruiseEndpoint;

    /**
     * Pobiera informację o wycieczce o podanym uuid
     */
    @GET
    @Path("/get-cruise/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public CruiseDto getCruiseByUUID(@PathParam("uuid") String uuid) throws BaseAppException {
//        return Response.ok().entity("That's alright, that's ok").build();
        return tryAndRepeat(() -> cruiseEndpoint.getCruise(UUID.fromString(uuid)));
    }

    /**
     * Pobiera wycieczki należące do grupy wycieczek o podanym uuid
     *
     * @param uuid uuid grupy wycieczek
     * @return listę wycieczek należących do grupy wycieczek o podanym uuid
     * @throws BaseAppException wyjątek rzucany w razie nie znależenia encji
     */
    @GET
    @Path("/cruise_group/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RelatedCruiseDto> getCruisesByCruiseGroupUUID(@PathParam("uuid") String uuid) throws BaseAppException {
        try {
            UUID convertedUUID = UUID.fromString(uuid);
            return tryAndRepeat(() -> cruiseEndpoint.getCruisesByCruiseGroup(convertedUUID));
        } catch (IllegalArgumentException e) {
            throw new MapperException(MAPPER_UUID_PARSE);
        }
    }

    /**
     * Pobiera informacje o opublikowanych wycieczkach
     *
     * @return Lista opublikowanych wycieczek
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    @GET
    @Path("/cruises")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CruiseDto> getAllCruises() throws BaseAppException {
        return tryAndRepeat(() -> cruiseEndpoint.getPublishedCruises());
    }

    /**
     * Pobiera informacje o wycieczkach dla danej grupy wycieczek
     *
     * @param cruiseGroupUUID UUID grupy wycieczek
     * @return Lista wycieczek w reprezentacji DTO
     * @throws BaseAppException Bazowy wyjątek aplikacji występujący w przypadku naruszenia reguł biznesowych
     */
    @GET
    @Path("/cruises-for-group/{cruise-group-uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CruiseForCruiseGroupDto> getCruisesForCruiseGroup(@PathParam("cruise-group-uuid") String cruiseGroupUUID) throws BaseAppException {
        try {
            UUID uuid = UUID.fromString(cruiseGroupUUID);
            return tryAndRepeat(() -> cruiseEndpoint.getCruisesForCruiseGroup(uuid));
        } catch (BaseAppException e) {
            throw new MapperException(MAPPER_UUID_PARSE);
        }
    }

    /**
     * Metoda tworząca rezerwację
     *
     * @param reservationDto Informacja o tworzonej rezerwacji
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    @POST
    @Path("/reserve")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createReservation(CreateReservationDto reservationDto) throws BaseAppException {
        tryAndRepeat(() -> reservationEndpoint.createReservation(reservationDto));
    }


    /**
     * Metoda anulująca rezerwację klienta
     *
     * @param reservationDTO Informacja o anulowanej rezerwacji
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    @DELETE
    @Path("/cancelReservation")
    @Consumes(MediaType.APPLICATION_JSON)
    public void cancelReservation(CancelReservationDTO reservationDTO) throws BaseAppException {
        tryAndRepeat(() -> reservationEndpoint.cancelReservation(reservationDTO));
    }

    /**
     * Metoda tworzącca nową wycieczkę
     *
     * @param newCruiseDto Obiekt reprezentujący nową wycieczkę
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @POST
    @Path("/new-cruise")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createCruise(@Valid @NotNull(message = CONSTRAINT_NOT_NULL) NewCruiseDto newCruiseDto) throws BaseAppException {
        tryAndRepeat(() -> cruiseEndpoint.addCruise(newCruiseDto));
    }


    /**
     * Metoda deaktywująca wycieczkę
     *
     * @param deactivateCruiseDto Obiekt posiadający UUID oraz werjsie danej wycieczki
     * @param etag                Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
     * @throws BaseAppException
     */
    @ETagFilterBinding
    @PUT
    @Path("/deactivate-cruise")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deactivateCruise(@Valid @NotNull(message = CONSTRAINT_NOT_NULL) DeactivateCruiseDto deactivateCruiseDto, @HeaderParam("If-Match") @NotNull(message = CONSTRAINT_NOT_NULL) @NotEmpty(message = CONSTRAINT_NOT_EMPTY) @Valid String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, deactivateCruiseDto)) {
            throw ControllerException.etagIdentityIntegrity();
        }
        tryAndRepeat(() -> cruiseEndpoint.deactivateCruise(deactivateCruiseDto));
    }


    @ETagFilterBinding
    @PUT
    @Path("/edit-cruise")
    @Consumes(MediaType.APPLICATION_JSON)
    public void editCruise(@Valid @NotNull(message = CONSTRAINT_NOT_NULL) EditCruiseDto editCruiseDto, @HeaderParam("If-Match") @NotNull(message = CONSTRAINT_NOT_NULL) @NotEmpty(message = CONSTRAINT_NOT_EMPTY) @Valid String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, editCruiseDto)) {
            throw ControllerException.etagIdentityIntegrity();
        }
        tryAndRepeat(() -> cruiseEndpoint.editCruise(editCruiseDto));
    }



    @ETagFilterBinding
    @PUT
    @Path("/publish")
    @Consumes(MediaType.APPLICATION_JSON)
    public void publishCruise(PublishCruiseDto publishCruiseDto,
                              @HeaderParam("If-Match") @NotNull(message = CONSTRAINT_NOT_NULL)
                              @NotEmpty(message = CONSTRAINT_NOT_EMPTY) @Valid String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, publishCruiseDto)) {
            throw ControllerException.etagIdentityIntegrity();
        }
        tryAndRepeat(()-> cruiseEndpoint.publishCruise(publishCruiseDto));
    }
}
