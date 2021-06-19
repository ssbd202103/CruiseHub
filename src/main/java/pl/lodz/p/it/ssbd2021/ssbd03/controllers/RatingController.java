package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.ratings.ClientRatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.RatingEndpointLocal;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static pl.lodz.p.it.ssbd2021.ssbd03.utils.TransactionRepeater.tryAndRepeat;

@Path("/ratings")
public class RatingController {
    @Inject
    private RatingEndpointLocal ratingEndpoint;

    @GET
    @Path("/{clientLogin}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClientRatingDto> getClientRatings(@PathParam("clientLogin") String clientLogin) throws BaseAppException {
        return tryAndRepeat(() -> ratingEndpoint.getClientRatings(clientLogin));
    }
}
