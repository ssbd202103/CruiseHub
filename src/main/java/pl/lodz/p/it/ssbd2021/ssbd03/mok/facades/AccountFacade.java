package pl.lodz.p.it.ssbd2021.ssbd03.mok.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.wrappers.LanguageTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateful
public class AccountFacade extends AbstractFacade<Object> {

    @PersistenceContext(unitName = "ssbd03mokPU")
    private EntityManager em;

    public AccountFacade() {
        super(Object.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AlterTypeWrapper getAlterTypeWrapperByAlterType(AlterType alterType) {
        TypedQuery<AlterTypeWrapper> tq = em.createNamedQuery("AlterTypeWrapper.findByName", AlterTypeWrapper.class);
        tq.setParameter("name", alterType);
        return tq.getSingleResult();
    }

    public LanguageTypeWrapper getLanguageTypeWrapperByLanguageType(LanguageType languageType) {
        TypedQuery<LanguageTypeWrapper> tq = em.createNamedQuery("LanguageTypeWrapper.findByName", LanguageTypeWrapper.class);
        tq.setParameter("name", languageType);
        return tq.getSingleResult();
    }

    public Account findByLogin(String login) throws BaseAppException {
        TypedQuery<Account> tq = em.createNamedQuery("Account.findByLogin", Account.class);
        tq.setParameter("login", login);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }
}
