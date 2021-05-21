package pl.lodz.p.it.ssbd2021.ssbd03.mow.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Rating;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AbstractFacade;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class RatingFacade extends AbstractFacade<Rating> {

    @PersistenceContext(unitName = "ssbd03mowPU")
    private EntityManager em;

    public RatingFacade() {
        super(Rating.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
