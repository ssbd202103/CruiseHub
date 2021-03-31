/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2021.ssbd03.mok.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels.Moderator;
/**
 *
 * @author student
 */
@Stateless
public class ModeratorFacade extends AbstractFacade<Moderator> {
    @PersistenceContext(unitName = "ssbd03mokPU")
    private EntityManager em;
    
    public ModeratorFacade() {
        super(Moderator.class );
    }

    @Override
    protected EntityManager getEntityManager() {
       return em;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
