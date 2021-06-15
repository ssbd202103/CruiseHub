package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.AttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.AttractionEndpointLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.utils.TransactionRepeater.tryAndRepeat;

@Path("/attractions")
@RequestScoped
public class AttractionController {

    @Inject
    AttractionEndpointLocal attractionEndpoint;

    @GET
    @Path("/cruise/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AttractionDto> getAttractionsByCruiseUUID(@PathParam("uuid") String uuid) throws BaseAppException {
        return tryAndRepeat(() -> attractionEndpoint.getAttractionsByCruiseUUID(UUID.fromString(uuid)));
    }
}
