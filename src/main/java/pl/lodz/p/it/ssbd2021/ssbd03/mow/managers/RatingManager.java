package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Rating;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.RatingFacade;

import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class RatingManager implements RatingManagerLocal {

    @EJB
    RatingFacade ratingFacade;

    @Override
    public void createRating(Rating rating) throws BaseAppException {

    }


}
