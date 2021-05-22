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

public class RatingFacadeMow extends AbstractFacade<Rating> {

    @PersistenceContext(unitName = "ssbd03mowPU")
    private EntityManager em;

    public RatingFacadeMow() {
        super(Rating.class);
    }

    public List<Rating> findByCruiseGroupName(String name) throws BaseAppException {
        TypedQuery<Rating> tq = em.createNamedQuery("Rating.findByCruiseGroupName", Rating.class);
        tq.setParameter("name", name);

        try {
            return tq.getResultList();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    public Rating findByCruiseNameAndAccountLogin(String name, String login) throws BaseAppException {
        TypedQuery<Rating> tq = em.createNamedQuery("Rating.findByCruiseGroupNameAndAccountLogin", Rating.class);
        tq.setParameter("name", name);
        tq.setParameter("login", login);

        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
