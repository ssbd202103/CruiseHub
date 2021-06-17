package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.AddAttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.AttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.AttractionEndpointLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
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

    /**
     * Metoda pozwalająca dodać atrakcję do wycieczki
     * @param addAttractionDto Reprezentacja Dto atrakcji
     * @return UUID utworzonej atrakcji
     * @throws BaseAppException Bazowy wyjątek aplikacji mogący wystąpić w przypadku naruszenia zasad biznesowych.
     */
    @POST
    @Path("/add-attraction")
    @Consumes(MediaType.APPLICATION_JSON)
    public UUID addAttraction(@Valid AddAttractionDto addAttractionDto) throws BaseAppException {
        return tryAndRepeat(() -> attractionEndpoint.addAttraction(addAttractionDto));
    }

    @DELETE
    @Path("/delete-attraction/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteAttraction(@PathParam("id") long id) throws BaseAppException {
        tryAndRepeat(() -> attractionEndpoint.deleteAttraction(id));
    }

}
