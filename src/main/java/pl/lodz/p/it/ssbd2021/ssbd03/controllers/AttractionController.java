package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.ControllerException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.MapperException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.AddAttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.AttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.EditAttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.RelatedCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.AttractionEndpointLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.MAPPER_UUID_PARSE;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.IntegrityUtils.checkEtagIntegrity;
import static pl.lodz.p.it.ssbd2021.ssbd03.utils.TransactionRepeater.tryAndRepeat;

@Path("/attractions")
@RequestScoped
public class AttractionController {

    @Inject
    AttractionEndpointLocal attractionEndpoint;

    @GET
    @Path("/cruise/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AttractionDto> getAttractionsByCruiseUUID(@PathParam("uuid") String attractionUUID) throws BaseAppException {
        try {
            UUID uuid = UUID.fromString(attractionUUID);
            return tryAndRepeat(attractionEndpoint, () -> attractionEndpoint.getAttractionsByCruiseUUID(uuid));
        } catch (IllegalArgumentException e) {
            throw new MapperException(MAPPER_UUID_PARSE);
        }
    }

    /**
     * Metoda pozwalająca dodać atrakcję do wycieczki
     *
     * @param addAttractionDto Reprezentacja Dto atrakcji
     * @return UUID utworzonej atrakcji
     * @throws BaseAppException Bazowy wyjątek aplikacji mogący wystąpić w przypadku naruszenia zasad biznesowych.
     */
    @POST
    @Path("/add-attraction")
    @Consumes(MediaType.APPLICATION_JSON)
    public UUID addAttraction(@Valid AddAttractionDto addAttractionDto) throws BaseAppException {
        return tryAndRepeat(attractionEndpoint, () -> attractionEndpoint.addAttraction(addAttractionDto));
    }

    @PUT
    @Path("/edit-attraction")
    public void editAttraction(@Valid EditAttractionDto editAttractionDto,
                               @HeaderParam("If-Match") String etag) throws BaseAppException {

        checkEtagIntegrity(editAttractionDto, etag);
        tryAndRepeat(attractionEndpoint, () -> attractionEndpoint.editAttraction(editAttractionDto));
    }

    /**
     * Metoda pozwalająca na usunięcie danej atrakcji z wycieczki która nie została jeszcze opublikowana
     *
     * @param uuid UUID atrakcji wybranej do usunięcia
     * @throws BaseAppException Bazowy wyjątek aplikacji mogący wystąpić w przypadku naruszenia zasad biznesowych.
     */
    @DELETE
    @Path("/delete-attraction/{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteAttraction(@PathParam("uuid") String uuid) throws BaseAppException {
     try{
         UUID convertedUUID = UUID.fromString(uuid);
         tryAndRepeat(attractionEndpoint, () -> attractionEndpoint.deleteAttraction(convertedUUID));
     }catch (IllegalArgumentException e) {
         throw new MapperException(MAPPER_UUID_PARSE);
     }
    }
}
