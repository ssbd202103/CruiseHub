package pl.lodz.p.it.ssbd2021.ssbd03.mow.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AbstractFacade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class CruiseGroupFacadeMow extends AbstractFacade<CruiseGroup> {

    @PersistenceContext(unitName = "ssbd03mowPU")
    private EntityManager em;

    public CruiseGroupFacadeMow() { super(CruiseGroup.class); }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CruiseGroup findByName(String name) throws BaseAppException {
        TypedQuery<CruiseGroup> tq = em.createNamedQuery("CruiseGroup.findByName", CruiseGroup.class);
        tq.setParameter("name", name);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

}