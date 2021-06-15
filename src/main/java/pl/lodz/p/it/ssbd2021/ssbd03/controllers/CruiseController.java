package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.CruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.CruiseForCruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.RelatedCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations.CancelReservationDTO;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations.CreateReservationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.NewCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.CruiseEndpointLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.ReservationEndpointLocal;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

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
    @Path("/{uuid}")
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
        return tryAndRepeat(() -> cruiseEndpoint.getCruisesByCruiseGroup(UUID.fromString(uuid)));
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
     * @param cruiseGroupName Nazwa grupy wycieczek
     * @return Lista wycieczek w reprezentacji DTO
     * @throws BaseAppException Bazowy wyjątek aplikacji występujący w przypadku naruszenia reguł biznesowych
     */
    @GET
    @Path("/cruises-for-group/{cruise-group-name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CruiseForCruiseGroupDto> getCruisesForCruiseGroup(@PathParam("cruise-group-name") String cruiseGroupName) throws BaseAppException {
        return tryAndRepeat(() -> cruiseEndpoint.getCruisesForCruiseGroup(cruiseGroupName));
    }

    /**
     * Metoda tworząca rezerwację
     *
     * @param reservationDto Informacja o tworzonej rezerwacji
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    @PUT
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

    @POST
    @Path("/new-cruise")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createCruise(@Valid @NotNull(message = CONSTRAINT_NOT_NULL) NewCruiseDto newCruiseDto) throws BaseAppException {
        tryAndRepeat(() -> cruiseEndpoint.addCruise(newCruiseDto));
    }
}
