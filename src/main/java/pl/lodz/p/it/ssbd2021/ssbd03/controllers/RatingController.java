package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
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

import static pl.lodz.p.it.ssbd2021.ssbd03.utils.TransactionRepeater.tryAndRepeat;

@Path("/ratings")
@RequestScoped
public class RatingController {

    @Inject
    private RatingEndpointLocal ratingEndpoint;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createRating(@Valid CreateRatingDto ratingDto) throws BaseAppException {
        tryAndRepeat(ratingEndpoint, () -> ratingEndpoint.createRating(ratingDto));
    }

    @DELETE
    @Path("/{uuid}")
    public void removeRating(@PathParam("uuid") String uuid) throws  BaseAppException {
        tryAndRepeat(ratingEndpoint, () -> ratingEndpoint.removeRating(UUID.fromString(uuid)));
    }

    @GET
    @Path("/{clientLogin}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClientRatingDto> getClientRatings(@PathParam("clientLogin") String clientLogin) throws BaseAppException {
        return tryAndRepeat(ratingEndpoint, () -> ratingEndpoint.getClientRatings(clientLogin));
    }

    @DELETE
    @Path("/{clientLogin}/{uuid}")
    public void removeClientRating(@PathParam("clientLogin") String clientLogin, @PathParam("uuid") String uuid) throws BaseAppException {
        tryAndRepeat(ratingEndpoint, () -> ratingEndpoint.removeClientRating(clientLogin, UUID.fromString(uuid)));
    }
}
