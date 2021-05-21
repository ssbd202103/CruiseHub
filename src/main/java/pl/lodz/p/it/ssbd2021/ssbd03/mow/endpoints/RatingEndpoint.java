package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CreateRatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.RatingManagerLocal;

import javax.ejb.EJB;

public class RatingEndpoint implements RatingEndpointLocal {

    @EJB
    RatingManagerLocal ratingManager;

    @Override
    public void createRating(CreateRatingDto createRatingDto) throws BaseAppException {

    }
}
