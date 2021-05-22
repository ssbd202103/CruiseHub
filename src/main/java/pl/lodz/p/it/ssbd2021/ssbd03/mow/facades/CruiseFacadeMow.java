package pl.lodz.p.it.ssbd2021.ssbd03.mow.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.UUID;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CruiseFacadeMow extends AbstractFacade<Cruise> {

    @PersistenceContext(unitName = "ssbd03mowPU")
    private EntityManager em;

    public CruiseFacadeMow() {
        super(Cruise.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Cruise findByUUID(UUID uuid) throws BaseAppException {
        TypedQuery<Cruise> tq = em.createNamedQuery("Cruise.findByUUID", Cruise.class);
        tq.setParameter("uuid", uuid.toString());
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }


}
