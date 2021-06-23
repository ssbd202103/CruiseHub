package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.common.dto.MetadataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.MapperException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CreateRatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.ratings.ClientRatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.RatingEndpointLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import java.util.List;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.MAPPER_UUID_PARSE;
import static pl.lodz.p.it.ssbd2021.ssbd03.utils.TransactionRepeater.tryAndRepeat;

@Path("/ratings")
@RequestScoped
public class RatingController {

    @Inject
    private RatingEndpointLocal ratingEndpoint;

    /**
     * Tworzy nową ocene dla wycieczki
     *
     * @param ratingDto obiekt reprezentujący
     *
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createRating(@Valid CreateRatingDto ratingDto) throws BaseAppException {
        tryAndRepeat(ratingEndpoint, () -> ratingEndpoint.createRating(ratingDto));
    }

    /**
     * Usuwa ocene dla wycieczki
     *
     * @param uuid grupy wycieczek
     *
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @DELETE
    @Path("/{uuid}")
    public void removeRating(@PathParam("uuid") String uuid) throws  BaseAppException {
        tryAndRepeat(ratingEndpoint, () -> ratingEndpoint.removeRating(UUID.fromString(uuid)));
    }

    /**
     * Pobiera liste ocen użytkownika
     *
     * @param clientLogin login klienta
     *
     * @return Lista ocen użytkownika
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @GET
    @Path("/{clientLogin}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClientRatingDto> getClientRatings(@PathParam("clientLogin") String clientLogin) throws BaseAppException {
        return tryAndRepeat(ratingEndpoint, () -> ratingEndpoint.getClientRatings(clientLogin));
    }

    /**
     * Usuwa ocene dla wycieczki danego użytkownika
     *
     * @param uuid grupy wycieczek
     * @param clientLogin login użytkownika
     *
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @DELETE
    @Path("/{clientLogin}/{uuid}")
    public void removeClientRating(@PathParam("clientLogin") String clientLogin, @PathParam("uuid") String uuid) throws BaseAppException {
        tryAndRepeat(ratingEndpoint, () -> ratingEndpoint.removeClientRating(clientLogin, UUID.fromString(uuid)));
    }

    /**
     * Pobiera metadane oceny
     *
     *@param uuid UUID oceny wybranej do metadanych
     * @return Reprezentacja DTO metadanych
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @GET
    @Path("/metadata/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public MetadataDto getRatingMetadata(@PathParam("uuid") String uuid) throws BaseAppException {
        try{
            UUID convertedUUID = UUID.fromString(uuid);
            return tryAndRepeat(ratingEndpoint, () -> ratingEndpoint.getRatingMetadata(convertedUUID));
        }catch (IllegalArgumentException e) {
            throw new MapperException(MAPPER_UUID_PARSE);
        }
    }

}
