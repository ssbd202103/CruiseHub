package pl.lodz.p.it.ssbd2021.ssbd03.mow.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.common.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Attraction;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@Stateless
@Interceptors(TrackingInterceptor.class)
public class AttractionFacadeMow extends AbstractFacade<Attraction> {

    @PersistenceContext(unitName = "ssbd03mowPU")
    private EntityManager em;

    public AttractionFacadeMow() {
        super(Attraction.class);
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    @PermitAll
    public List<Attraction> findAll() throws FacadeException {
        return super.findAll();
    }

    @Override
    @RolesAllowed("editAttraction")
    public void edit(Attraction entity) throws FacadeException {
        super.edit(entity);
    }

    @Override
    @RolesAllowed("addAttraction")
    public void create(Attraction entity) throws FacadeException {
        super.create(entity);
    }

    @RolesAllowed("deleteAttraction")
    @Override
    public void remove(Attraction entity) throws FacadeException {
        super.remove(entity);
    }


    @PermitAll
    public Attraction findByUUID(UUID uuid) throws BaseAppException {
        TypedQuery<Attraction> tq = em.createNamedQuery("Attraction.findByUUID", Attraction.class);
        tq.setParameter("uuid", uuid);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    @RolesAllowed("createReservation")
    public long getNumberOfTakenSeats(Attraction attraction) {
        TypedQuery<Long> tq = em.createNamedQuery("Attraction.countReservedSpots", Long.class);
        tq.setParameter("attraction", attraction);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }


    @PermitAll
    public List<Attraction> findByCruiseUUID(UUID uuid) throws BaseAppException {
        TypedQuery<Attraction> tq = em.createNamedQuery("Attraction.findByCruiseUUID", Attraction.class);
        tq.setParameter("uuid", uuid);
        try {
            return tq.getResultList();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

}