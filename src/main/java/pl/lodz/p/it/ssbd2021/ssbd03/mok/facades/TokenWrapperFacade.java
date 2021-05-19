package pl.lodz.p.it.ssbd2021.ssbd03.mok.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.TokenWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TokenWrapperFacade extends AbstractFacade<TokenWrapper> {
    @PersistenceContext(unitName = "ssbd03mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TokenWrapperFacade() {
        super(TokenWrapper.class);
    }

    public List<TokenWrapper> getUsedToken() {
        TypedQuery<TokenWrapper> tq = em.createNamedQuery("TokenWrapper.findUsed", TokenWrapper.class);
        return tq.getResultList();
    }

    public List<TokenWrapper> getUnUsedToken() {
        TypedQuery<TokenWrapper> tq = em.createNamedQuery("TokenWrapper.findUnUsed", TokenWrapper.class);
        return tq.getResultList();
    }

    public TokenWrapper findByToken(String token) {
        TypedQuery<TokenWrapper> tq = em.createNamedQuery("TokenWrapper.findByToken", TokenWrapper.class);
        tq.setParameter("token", token);
        return tq.getSingleResult();
    }

    @Override
    public void create(TokenWrapper entity) throws FacadeException {
        super.create(entity);
    }

    @Override
    public void edit(TokenWrapper entity) throws FacadeException {
        super.edit(entity);
    }
}
