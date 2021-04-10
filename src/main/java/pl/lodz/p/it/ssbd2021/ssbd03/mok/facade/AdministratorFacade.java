package pl.lodz.p.it.ssbd2021.ssbd03.mok.facade;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels.Administrator;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AdministratorFacade extends AbstractFacade<Administrator> {
    @PersistenceContext(unitName = "ssbd03mokPU")
    private EntityManager em;

    public AdministratorFacade() {
        super(Administrator.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
