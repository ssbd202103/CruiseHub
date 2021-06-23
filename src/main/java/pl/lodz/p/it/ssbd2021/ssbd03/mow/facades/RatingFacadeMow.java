package pl.lodz.p.it.ssbd2021.ssbd03.mow.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.common.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Rating;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.enterprise.context.RequestScoped;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

import static javax.ejb.TransactionAttributeType.MANDATORY;

@TransactionAttribute(MANDATORY)
@Stateless
@Interceptors(TrackingInterceptor.class)
public class RatingFacadeMow extends AbstractFacade<Rating> {

    @PersistenceContext(unitName = "ssbd03mowPU")
    private EntityManager em;

    public RatingFacadeMow() {
        super(Rating.class);
    }

    public List<Rating> findByCruiseGroupUUID(UUID uuid) throws BaseAppException {
        TypedQuery<Rating> tq = em.createNamedQuery("Rating.findByCruiseGroupUUID", Rating.class);
        tq.setParameter("uuid", uuid);

        try {
            return tq.getResultList();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    public Rating findByCruiseGroupUUIDAndAccountLogin(UUID uuid, String login) throws BaseAppException {
        TypedQuery<Rating> tq = em.createNamedQuery("Rating.findByCruiseGroupUUIDAndAccountLogin", Rating.class);
        tq.setParameter("uuid", uuid);
        tq.setParameter("login", login);

        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    public Long countByLogin(String login, UUID uuid) throws BaseAppException {
        TypedQuery<Long> tq = em.createNamedQuery("Rating.countByCruiseGroupUUIDAndAccountLogin", Long.class);
        tq.setParameter("login", login);
        tq.setParameter("uuid", uuid);

        return tq.getSingleResult();
    }

    public Rating findByCruiseUuidAndAccountLogin(String login, UUID uuid) throws BaseAppException {
        TypedQuery<Rating> tq = em.createNamedQuery("Rating.findByUuidAndAccountLogin", Rating.class);
        tq.setParameter("login", login);
        tq.setParameter("uuid", uuid);

        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    @RolesAllowed("authenticatedUser")
    public Rating findRatingByUuid(UUID uuid) throws FacadeException {
        TypedQuery<Rating> tq = em.createNamedQuery("Rating.findByUuid", Rating.class);
        tq.setParameter("uuid", uuid);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    public List<Rating> findUserRatings(String login) throws BaseAppException {
        TypedQuery<Rating> tq = em.createNamedQuery("Rating.findUserRatings", Rating.class);
        tq.setParameter("login", login);
        try {
            return tq.getResultList();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    @RolesAllowed({"removeRating", "removeClientRating"})
    @Override
    public void remove(Rating entity) throws FacadeException {
        super.remove(entity);
    }

    @RolesAllowed("createRating")
    @Override
    public void create(Rating entity) throws FacadeException {
        super.create(entity);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
