package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.CruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.CruiseForCruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.CruiseEndpointLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
}
