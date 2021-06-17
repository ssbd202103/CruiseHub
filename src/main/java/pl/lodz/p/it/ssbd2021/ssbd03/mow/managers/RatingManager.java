package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Rating;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.AccountFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseGroupFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.RatingFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import java.util.List;
import java.util.UUID;

import static javax.ejb.TransactionAttributeType.MANDATORY;

@Stateful
@TransactionAttribute(MANDATORY)
@Interceptors(TrackingInterceptor.class)
public class RatingManager implements RatingManagerLocal {

    @Context
    SecurityContext securityContext;

    @Inject
    RatingFacadeMow ratingFacade;

    @Inject
    AccountFacadeMow accountFacadeMow;

    @Inject
    CruiseGroupFacadeMow cruiseGroupFacadeMow;

    @RolesAllowed("createRating")
    @Override
    public void createRating(String login, UUID cruiseGroupUUID, Double rating) throws BaseAppException {
        Account account = accountFacadeMow.findByLogin(login);
        CruiseGroup cruiseGroup = cruiseGroupFacadeMow.findByUUID(cruiseGroupUUID);
        Rating r = new Rating(account, cruiseGroup, rating);
        r.setAlteredBy(account);
        r.setCreatedBy(account);
        r.setAlterType(account.getAlterType());

        ratingFacade.create(r);

        List<Rating> ratings = ratingFacade.findByCruiseGroupUUID(cruiseGroupUUID);
        Double avgRating = ratings.stream().mapToDouble(Rating::getRating).sum() / ratings.size();

        cruiseGroup.setAverageRating(avgRating);
        cruiseGroupFacadeMow.edit(cruiseGroup);
    }

    @RolesAllowed("removeRating")
    @Override
    public void removeRating(String login, UUID cruiseGroupUUId) throws BaseAppException {
        Rating r = ratingFacade.findByCruiseGroupUUIDAndAccountLogin(cruiseGroupUUId, login);

        ratingFacade.remove(r);
    }

    @RolesAllowed("ownFindRating")
    @Override
    public Rating getRating(String login, String cruiseGroupName) throws BaseAppException {
        return null; // todo finish implementation
    }

    @RolesAllowed("removeClientRating")
    @Override
    public void removeClientRating(String login, String cruiseGroupName) throws BaseAppException {
        // todo finish implementantion
    }
}
