package pl.lodz.p.it.ssbd2021.ssbd03.mow.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Rating;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AbstractFacade;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class RatingFacade extends AbstractFacade<Rating> {

    @PersistenceContext(unitName = "ssbd03mowPU")
    private EntityManager em;

    public RatingFacade() {
        super(Rating.class);
    }

    public List findByCruiseName(String name) throws BaseAppException {
        TypedQuery<Rating> tq = em.createNamedQuery("Rating.findByCruiseGroupId", Rating.class);
        tq.setParameter("name", name);

        try {
            return tq.getResultList();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
