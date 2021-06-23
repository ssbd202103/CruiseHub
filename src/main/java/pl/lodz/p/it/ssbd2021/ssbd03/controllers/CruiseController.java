package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.common.dto.MetadataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.ControllerException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.MapperException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.*;
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
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.IntegrityUtils.checkEtagIntegrity;
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
     *
     * @return obiekt reprezentujący wycieczke
     * @throws BaseAppException wyjątek rzucany w razie nie znależenia encji
     */
    @GET
    @Path("/get-cruise/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public CruiseDto getCruiseByUUID(@PathParam("uuid") String strUUID) throws BaseAppException {
        UUID uuid;
        try {
            uuid = UUID.fromString(strUUID);
        } catch (IllegalArgumentException e) {
            throw new ControllerException(NO_SUCH_ELEMENT_ERROR);
        }
        return tryAndRepeat(cruiseEndpoint, () -> cruiseEndpoint.getCruise(UUID.fromString(strUUID)));
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
            return tryAndRepeat(cruiseEndpoint, () -> cruiseEndpoint.getCruisesByCruiseGroup(convertedUUID));
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
    public List<CruiseGroupWithCruisesDto> getAllCruises() throws BaseAppException {
        return tryAndRepeat(cruiseEndpoint, () -> cruiseEndpoint.getPublishedCruises());
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
            return tryAndRepeat(cruiseEndpoint, () -> cruiseEndpoint.getCruisesForCruiseGroup(uuid));
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
    public void createReservation(@Valid CreateReservationDto reservationDto) throws BaseAppException {
        tryAndRepeat(reservationEndpoint, () -> reservationEndpoint.createReservation(reservationDto));
    }


    /**
     * Metoda anulująca rezerwację klienta
     *
     * @param reservationUUID UUID anulowanej rezerwacji
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    @DELETE
    @Path("/cancelReservation/{reservationUUID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void cancelReservation(@PathParam("reservationUUID") UUID reservationUUID) throws BaseAppException { //todo change UUID to String and handle parsing exception
        tryAndRepeat(reservationEndpoint, () -> reservationEndpoint.cancelReservation(reservationUUID));
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
        tryAndRepeat(cruiseEndpoint, () -> cruiseEndpoint.addCruise(newCruiseDto));
    }


    /**
     * Metoda deaktywująca wycieczkę
     *
     * @param deactivateCruiseDto Obiekt posiadający UUID oraz werjsie danej wycieczki
     * @param etag                Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @ETagFilterBinding
    @PUT
    @Path("/deactivate-cruise")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deactivateCruise(@Valid @NotNull(message = CONSTRAINT_NOT_NULL) DeactivateCruiseDto deactivateCruiseDto, @HeaderParam("If-Match") @NotNull(message = CONSTRAINT_NOT_NULL) @NotEmpty(message = CONSTRAINT_NOT_EMPTY) @Valid String etag) throws BaseAppException {
        checkEtagIntegrity(deactivateCruiseDto, etag);
        tryAndRepeat(cruiseEndpoint, () -> cruiseEndpoint.deactivateCruise(deactivateCruiseDto));
    }


    /**
     * @param editCruiseDto Obiekt reprezentujący wycieczkę
     * @param etag          Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @ETagFilterBinding
    @PUT
    @Path("/edit-cruise")
    @Consumes(MediaType.APPLICATION_JSON)
    public void editCruise(@Valid @NotNull(message = CONSTRAINT_NOT_NULL) EditCruiseDto editCruiseDto, @HeaderParam("If-Match") @NotNull(message = CONSTRAINT_NOT_NULL) @NotEmpty(message = CONSTRAINT_NOT_EMPTY) @Valid String etag) throws BaseAppException {
        checkEtagIntegrity(editCruiseDto, etag);
        tryAndRepeat(cruiseEndpoint, () -> cruiseEndpoint.editCruise(editCruiseDto));
    }

    /**
     * Zmienia status wycieczki na opublikowany
     *
     * @param publishCruiseDto obiekt reprezentujący wycieczke
     * @param etag          Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @ETagFilterBinding
    @PUT
    @Path("/publish")
    @Consumes(MediaType.APPLICATION_JSON)
    public void publishCruise(@Valid PublishCruiseDto publishCruiseDto,
                              @HeaderParam("If-Match") @NotNull(message = CONSTRAINT_NOT_NULL)
                              @NotEmpty(message = CONSTRAINT_NOT_EMPTY) @Valid String etag) throws BaseAppException {
        checkEtagIntegrity(publishCruiseDto, etag);
        tryAndRepeat(cruiseEndpoint, () -> cruiseEndpoint.publishCruise(publishCruiseDto));
    }

    /**
     * Pobiera metadane wycieczki
     *
     * @param uuid UUID wycieczki wybranej do metadanych
     * @return Reprezentacja DTO metadanych
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @GET
    @Path("/metadata/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public MetadataDto getCruiseMetadata(@PathParam("uuid") String uuid) throws BaseAppException {
        try{
            UUID convertedUUID = UUID.fromString(uuid);
            return tryAndRepeat(cruiseEndpoint, () -> cruiseEndpoint.getCruiseMetadata(convertedUUID));
        }catch (IllegalArgumentException e) {
            throw new MapperException(MAPPER_UUID_PARSE);
        }
    }

}
