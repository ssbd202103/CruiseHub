package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Rating;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RemoveClientRatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RemoveRankingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.RatingMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.RatingManagerLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;

@TransactionAttribute(REQUIRES_NEW)
@Interceptors(TrackingInterceptor.class)
public class RatingEndpoint implements RatingEndpointLocal {

    @Inject
    RatingManagerLocal ratingManager;

    @Override
    public void createRating(RatingDto ratingDto) throws BaseAppException {
        ratingManager.createRating(ratingDto.getLogin(), ratingDto.getCruiseGroupName(), ratingDto.getRating());
    }

    @Override
    public void removeRating(RemoveRankingDto removeRankingDto) throws BaseAppException {
        ratingManager.removeRating(removeRankingDto.getLogin(), removeRankingDto.getCruiseGroupName());
    }

    @RolesAllowed("ownFindRating")
    @Override
    public RatingDto getRating(String login, String cruiseGroupName) throws BaseAppException {
        return null; // todo finish implementation
    }

    @RolesAllowed("removeClientRating")
    @Override
    public void removeClientRating(RemoveClientRatingDto removeClientRatingDto) throws BaseAppException {
        // todo finish implementation
    }
}
