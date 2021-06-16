package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints.BaseEndpoint;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.MapperException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RemoveClientRatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RemoveRankingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.RatingMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.RatingManagerLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import java.util.UUID;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.RATING_MAPPER_UUID_PARSE;

@TransactionAttribute(REQUIRES_NEW)
@Interceptors(TrackingInterceptor.class)
@Stateful
public class RatingEndpoint extends BaseEndpoint implements RatingEndpointLocal {

    @Inject
    RatingManagerLocal ratingManager;

    @RolesAllowed("createRating")
    @Override
    public void createRating(RatingDto ratingDto) throws BaseAppException {
        ratingManager.createRating(ratingDto.getLogin(), ratingDto.getCruiseGroupName(), ratingDto.getRating());
    }

    @RolesAllowed("removeRating")
    @Override
    public void removeRating(RemoveRankingDto removeRankingDto) throws BaseAppException {
        ratingManager.removeRating(removeRankingDto.getLogin(), removeRankingDto.getCruiseGroupName());
    }

    @RolesAllowed("ownFindRating")
    @Override
    public RatingDto getRating(String uuidStr) throws BaseAppException {
        try {
            return RatingMapper.mapRatingToRatingDto(ratingManager.getOwnRating(UUID.fromString(uuidStr)));
        } catch (IllegalArgumentException e) {
            throw new MapperException(RATING_MAPPER_UUID_PARSE);
        }
    }

    @RolesAllowed("removeClientRating")
    @Override
    public void removeClientRating(RemoveClientRatingDto removeClientRatingDto) throws BaseAppException {
        // todo finish implementation
    }
}
