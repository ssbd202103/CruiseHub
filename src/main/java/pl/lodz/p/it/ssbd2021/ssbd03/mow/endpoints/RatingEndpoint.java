package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints.BaseEndpoint;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Rating;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.MapperException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CreateRatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RemoveRankingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.ratings.ClientRatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.ratings.RatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.ratings.RemoveClientRatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.ratings.RemoveRankingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations.SelfReservationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.RatingMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.ReservationMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.RatingManagerLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void createRating(CreateRatingDto ratingDto) throws BaseAppException {
        ratingManager.createRating(ratingDto.getLogin(), ratingDto.getCruiseGroupUUID(), ratingDto.getRating());
    }

    @RolesAllowed("removeRating")
    @Override
    public void removeRating(RemoveRankingDto removeRankingDto) throws BaseAppException {
        ratingManager.removeRating(removeRankingDto.getLogin(), removeRankingDto.getCruiseGroupUUID());
    }

    @RolesAllowed("ownFindRating")
    @Override
    public List<RatingDto> getOwnRatings() throws BaseAppException {
        try {
            return ratingManager.getOwnRatings().stream().map(RatingMapper::toRatingDto).collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new MapperException(RATING_MAPPER_UUID_PARSE);
        }
    }

    @RolesAllowed("getRemoveClientRating")
    public List<ClientRatingDto> getClientRatings(String login) throws BaseAppException {
        List<ClientRatingDto> res = new ArrayList<>();
        for (Rating rating : ratingManager.getClientRatings(login)) {
            res.add(RatingMapper.toClientRatingDto(login, rating));
        }
        return res;
    }

    @RolesAllowed("getRemoveClientRating")
    @Override
    public void removeClientRating(RemoveClientRatingDto removeClientRatingDto) throws BaseAppException {
        // todo finish implementation
    }
}
