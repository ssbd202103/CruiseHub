package pl.lodz.p.it.ssbd2021.ssbd03.mok.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EntityDetailsFacade extends AbstractFacade<BaseEntity> {
    @PersistenceContext(unitName = "ssbd03mokPU")
    private EntityManager em;

    public EntityDetailsFacade() {
        super(BaseEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
