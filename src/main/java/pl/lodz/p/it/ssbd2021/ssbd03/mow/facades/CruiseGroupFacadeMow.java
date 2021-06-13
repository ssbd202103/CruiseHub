package pl.lodz.p.it.ssbd2021.ssbd03.mow.facades;


import pl.lodz.p.it.ssbd2021.ssbd03.common.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
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
public class CruiseGroupFacadeMow extends AbstractFacade<CruiseGroup> {

    @PersistenceContext(unitName = "ssbd03mowPU")
    private EntityManager em;

    public CruiseGroupFacadeMow() {
        super(CruiseGroup.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

   @PermitAll
    @Override
    public List<CruiseGroup> findAll() throws FacadeException { //TODO throws FacadeException {
        return super.findAll();
    }

    @Override
    @RolesAllowed("changeCruiseGroup")
    public void edit(CruiseGroup entity) throws FacadeException { //TODo throws FacadeException {
        super.edit(entity);
    }

    @Override
    @RolesAllowed("addCruiseGroup")
    public void create(CruiseGroup entity) throws FacadeException { //TODO throws FacadeException {
        super.create(entity);
    }

    @PermitAll
    public CruiseGroup findByName(String name) throws BaseAppException {
        TypedQuery<CruiseGroup> tq = em.createNamedQuery("CruiseGroup.findByName", CruiseGroup.class);
        tq.setParameter("name", name);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    @PermitAll
    public CruiseGroup findByUUID(UUID uuid) throws BaseAppException {
        TypedQuery<CruiseGroup> tq = em.createNamedQuery("CruiseGroup.findByUUID", CruiseGroup.class);
        tq.setParameter("uuid", uuid.toString());
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

   @RolesAllowed("getAllCruiseGroupList")
    public List<Cruise> findCruisesForCruiseGroup(CruiseGroup cruiseGroup) throws FacadeException {
        TypedQuery<Cruise> tq = em.createNamedQuery("CruiseGroup.findCruises", Cruise.class);
        tq.setParameter("name", cruiseGroup);
        try {
            return tq.getResultList();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }

    }
}