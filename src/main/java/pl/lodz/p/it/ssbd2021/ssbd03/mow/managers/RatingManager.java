package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Attraction;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Rating;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.RatingExistsException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseGroupFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.RatingFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.UUID;

import static javax.ejb.TransactionAttributeType.MANDATORY;

@Stateful
@TransactionAttribute(MANDATORY)
@Interceptors(TrackingInterceptor.class)
public class RatingManager extends BaseManagerMow implements RatingManagerLocal {

    @Inject
    private RatingFacadeMow ratingFacade;

    @Inject
    private CruiseGroupFacadeMow cruiseGroupFacadeMow;

    @RolesAllowed("createRating")
    @Override
    public void createRating(UUID cruiseGroupUUID, Double rating) throws BaseAppException {
        Account account = getCurrentUser();
        CruiseGroup cruiseGroup = cruiseGroupFacadeMow.findByUUID(cruiseGroupUUID);

        Long count = ratingFacade.countByLogin(account.getLogin(), cruiseGroupUUID);

        if (count > 0) throw new RatingExistsException(I18n.RATING_EXISTS);

        Rating r = new Rating(account, cruiseGroup, rating);
        r.setAlteredBy(account);
        r.setCreatedBy(account);
        r.setAlterType(account.getAlterType());

        ratingFacade.create(r);

        calculateAverageRating(cruiseGroup);
    }

    @RolesAllowed("removeRating")
    @Override
    public void removeRating(UUID cruiseGroupUUID) throws BaseAppException {
        Account account = getCurrentUser();
        Rating r = ratingFacade.findByCruiseGroupUUIDAndAccountLogin(cruiseGroupUUID, account.getLogin());

        ratingFacade.remove(r);

        CruiseGroup cruiseGroup = cruiseGroupFacadeMow.findByUUID(cruiseGroupUUID);

        calculateAverageRating(cruiseGroup);
    }

    private void calculateAverageRating(CruiseGroup cruiseGroup) throws BaseAppException {
        List<Rating> ratings = ratingFacade.findByCruiseGroupUUID(cruiseGroup.getUuid());
        double avgRating = 0.0;
        if (ratings.size() > 0) {
            avgRating = ratings.stream().mapToDouble(Rating::getRating).sum() / ratings.size();
        }

        cruiseGroup.setAverageRating(avgRating);
        setUpdatedMetadata(cruiseGroup);
        cruiseGroupFacadeMow.edit(cruiseGroup);
    }

    @RolesAllowed("ownFindRating")
    @Override
    public List<Rating> getOwnRatings() throws BaseAppException {
        return ratingFacade.findUserRatings(getCurrentUser().getLogin());
    }

    @RolesAllowed("getClientRating")
    @Override
    public List<Rating> getClientRatings(String login) throws BaseAppException {
        return ratingFacade.findUserRatings(login);
    }

    @RolesAllowed("removeClientRating")
    @Override
    public void removeClientRating(String login, UUID cruiseGroupUUID) throws BaseAppException {
        Rating rating = ratingFacade.findByCruiseGroupUUIDAndAccountLogin(cruiseGroupUUID, login);
        ratingFacade.remove(rating);
        CruiseGroup cruiseGroup = cruiseGroupFacadeMow.findByUUID(cruiseGroupUUID);
        calculateAverageRating(cruiseGroup);
    }
}
