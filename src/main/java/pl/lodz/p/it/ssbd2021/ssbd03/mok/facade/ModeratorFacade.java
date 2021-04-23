package pl.lodz.p.it.ssbd2021.ssbd03.mok.facade;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Moderator;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ModeratorFacade extends AbstractFacade<Moderator> {

    @PersistenceContext(unitName = "ssbd03mokPU")
    private EntityManager em;

    public ModeratorFacade() {
        super(Moderator.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
