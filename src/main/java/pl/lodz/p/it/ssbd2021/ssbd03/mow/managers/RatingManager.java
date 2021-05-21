package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Rating;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.RatingFacade;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@Stateful
public class RatingManager implements RatingManagerLocal {

    @Context
    SecurityContext securityContext;

    @EJB
    RatingFacade ratingFacade;

    @EJB
    AccountFacade accountFacade;

    @Override
    public void createRating(String login, String cruiseName, Integer ranting) throws BaseAppException {
        //TODO
        Account account = accountFacade.findByLogin(login);
    }

    @Override
    public void removeRating(String login, String cruiseName) throws BaseAppException {
        //TODO
        Account account = accountFacade.findByLogin(login);
    }
}
