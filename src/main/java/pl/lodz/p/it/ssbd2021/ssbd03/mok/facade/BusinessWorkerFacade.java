package pl.lodz.p.it.ssbd2021.ssbd03.mok.facade;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels.BusinessWorker;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class BusinessWorkerFacade extends AbstractFacade<BusinessWorker> {

    @PersistenceContext(unitName = "ssbd03mokPU")
    private EntityManager em;

    public BusinessWorkerFacade() {
        super(BusinessWorker.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
