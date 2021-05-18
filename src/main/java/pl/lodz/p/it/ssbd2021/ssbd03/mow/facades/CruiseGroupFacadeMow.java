package pl.lodz.p.it.ssbd2021.ssbd03.mow.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AbstractFacade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CruiseGroupFacadeMow extends AbstractFacade<CruiseGroup> {

    @PersistenceContext(unitName = "ssbd03mowPU")
    private EntityManager em;

    public CruiseGroupFacadeMow() { super(CruiseGroup.class); }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}