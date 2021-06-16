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
    public void createRating(String login, String cruiseName, Integer rating) throws BaseAppException {
        Account account = accountFacadeMow.findByLogin(login);
        CruiseGroup cruiseGroup = cruiseGroupFacadeMow.findByName(cruiseName);
        Rating r = new Rating(account, cruiseGroup, rating);

        ratingFacade.create(r);
    }

    @RolesAllowed("removeRating")
    @Override
    public void removeRating(String login, String cruiseName) throws BaseAppException {
        Rating r = ratingFacade.findByCruiseNameAndAccountLogin(cruiseName, login);

        ratingFacade.remove(r);
    }

    @RolesAllowed("ownFindRating")
    @Override
    public List<Rating> getOwnRatings() throws BaseAppException {
        return ratingFacade.findOwnRatings(getCurrentUser().getLogin());
    }

    @RolesAllowed("removeClientRating")
    @Override
    public void removeClientRating(String login, String cruiseGroupName) throws BaseAppException {
        // todo finish implementantion
    }

    private Account getCurrentUser() throws BaseAppException {
        return accountFacadeMow.findByLogin(securityContext.getUserPrincipal().getName());
    }
}
