package pl.lodz.p.it.ssbd2021.ssbd03.mow.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CruiseFacade extends AbstractFacade<Cruise> {

    @PersistenceContext(unitName = "ssbd03mowPU")
    private EntityManager em;

    public CruiseFacade() {
        super(Cruise.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }




}
