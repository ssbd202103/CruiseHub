package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.NewCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.CruiseEndpointLocal;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
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
     * @return Lista opublikowanych wycieczek
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    @GET
    @Path("/cruises")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CruiseDto> getAllCruises() throws BaseAppException {
        return tryAndRepeat(() -> cruiseEndpoint.getPublishedCruises());
    }

    @POST
    @Path("/new-cruise")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createCruise(@Valid @NotNull(message = CONSTRAINT_NOT_NULL) NewCruiseDto newCruiseDto) throws BaseAppException {
        tryAndRepeat(() -> cruiseEndpoint.addCruise(newCruiseDto));
    }

}
