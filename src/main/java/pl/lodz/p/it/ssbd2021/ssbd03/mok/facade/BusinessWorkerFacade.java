/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2021.ssbd03.mok.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels.BusinessWorker;

/**
 *
 * @author student
 */
@Stateless
public class BusinessWorkerFacade extends AbstractFacade<BusinessWorker>{

    @PersistenceContext(unitName = "ssbd03mokPU")
    private EntityManager em;
    
    public BusinessWorkerFacade() {
        super(BusinessWorker.class );
    }

    @Override
    protected EntityManager getEntityManager() {
       return em;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
