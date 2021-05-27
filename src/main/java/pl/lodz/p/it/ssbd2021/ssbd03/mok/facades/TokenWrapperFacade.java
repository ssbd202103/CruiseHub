package pl.lodz.p.it.ssbd2021.ssbd03.mok.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.TokenWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(TrackingInterceptor.class)
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

    public List<TokenWrapper> getUnusedToken() {
        TypedQuery<TokenWrapper> tq = em.createNamedQuery("TokenWrapper.findUnused", TokenWrapper.class);
        return tq.getResultList();
    }

    public TokenWrapper findByToken(String token) throws BaseAppException {
        TypedQuery<TokenWrapper> tq = em.createNamedQuery("TokenWrapper.findByToken", TokenWrapper.class);
        tq.setParameter("token", token);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    @Override
    public void create(TokenWrapper entity) throws FacadeException {
        super.create(entity);
    }

    @Override
    public void edit(TokenWrapper entity) throws FacadeException {
        super.edit(entity);
    }

    @Override
    public void remove(TokenWrapper entity) throws FacadeException {
        super.remove(entity);
    }
}
