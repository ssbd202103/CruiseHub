package pl.lodz.p.it.ssbd2021.ssbd03.mow.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.common.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(TrackingInterceptor.class)
public class CruiseFacadeMow extends AbstractFacade<Cruise> {

    @PersistenceContext(unitName = "ssbd03mowPU")
    private EntityManager em;

    public CruiseFacadeMow() {
        super(Cruise.class);
    }

    @Override
    @RolesAllowed("editCruise")
    protected void edit(Cruise entity) throws FacadeException {
        super.edit(entity);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @PermitAll
//    @RolesAllowed({"deactivateCruise", "editCruise", "viewCruiseReservations", "getWorkerCruiseReservations", "createReservation", "cancelReservation"})
    public Cruise findByUUID(UUID uuid) throws BaseAppException {
        TypedQuery<Cruise> tq = em.createNamedQuery("Cruise.findByUUID", Cruise.class);
        tq.setParameter("uuid", uuid);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    @PermitAll
    public List<Cruise> getPublishedCruises() {
        TypedQuery<Cruise> tq = em.createNamedQuery("Cruise.findAllPublished", Cruise.class);
        return tq.getResultList();
    }

    // TODO Roles!!!
    @PermitAll
    public List<Cruise> findByCruiseGroupUUID(UUID uuid) throws BaseAppException {
        TypedQuery<Cruise> tq = em.createNamedQuery("Cruise.findByCruiseGroupUUID", Cruise.class);
        tq.setParameter("uuid", uuid);

        try {
            return tq.getResultList();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    @RolesAllowed("addCruise")
    @Override
    public void create(Cruise entity) throws FacadeException {
        super.create(entity);
    }

    @PermitAll
    public List<Cruise> getCruisesForCruiseGroup(String cruiseGroupName) {
        TypedQuery<Cruise> tq = em.createNamedQuery("Cruise.findByCruiseGroup", Cruise.class);
        tq.setParameter("cruiseGroupName", cruiseGroupName);
        return tq.getResultList();
    }
}
