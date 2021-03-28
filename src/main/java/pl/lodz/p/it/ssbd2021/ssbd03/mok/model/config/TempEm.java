package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.config;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.Account;

import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TempEm {
    @PersistenceContext(unitName = "temp")
    private EntityManager em;

    public void create(Object obj){
        em.persist(obj);
//        em.flush();
    }
}
