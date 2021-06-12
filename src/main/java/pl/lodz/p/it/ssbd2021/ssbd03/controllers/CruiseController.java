package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RelatedCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.CruiseEndpointLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.utils.TransactionRepeater.tryAndRepeat;

/**
 * Klasa udostepniajaca API do wykonywania operacji na wycieczkach
 */
@Path("/cruise")
@RequestScoped
public class CruiseController {

    @Inject
    private CruiseEndpointLocal cruiseEndpoint;

    /**
     * Pobiera informację o wycieczce o podanym uuid
     *
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
     * @return Lista opublikowanych wycieczek
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    @GET
    @Path("/cruises")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CruiseDto> getAllCruises() throws BaseAppException {
        return tryAndRepeat(() -> cruiseEndpoint.getPublishedCruises());
    }
}
