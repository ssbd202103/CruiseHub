package pl.lodz.p.it.ssbd2021.ssbd03.mok.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.common.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.TokenWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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

    @PermitAll
// @RolesAllowed("SYSTEM")
    public List<TokenWrapper> getUsedToken() {
        TypedQuery<TokenWrapper> tq = em.createNamedQuery("TokenWrapper.findUsed", TokenWrapper.class);
        return tq.getResultList();
    }

    @PermitAll
// @RolesAllowed("SYSTEM")
    public List<TokenWrapper> getUnusedToken() {
        TypedQuery<TokenWrapper> tq = em.createNamedQuery("TokenWrapper.findUnused", TokenWrapper.class);
        return tq.getResultList();
    }

    @PermitAll
    public TokenWrapper findByToken(String token) throws BaseAppException {
        TypedQuery<TokenWrapper> tq = em.createNamedQuery("TokenWrapper.findByToken", TokenWrapper.class);
        tq.setParameter("token", token);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    @PermitAll
    @Override
    public void create(TokenWrapper entity) throws FacadeException {
        super.create(entity);
    }

    @PermitAll
    @Override
    public void edit(TokenWrapper entity) throws FacadeException {
        super.edit(entity);
    }

    @PermitAll
    //    @RolesAllowed("SYSTEM")
    @Override
    public void remove(TokenWrapper entity) throws FacadeException {
        super.remove(entity);
    }
}
