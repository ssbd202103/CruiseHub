package pl.lodz.p.it.ssbd2021.ssbd03.mok.facades;

import org.hibernate.exception.ConstraintViolationException;
import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.wrappers.LanguageTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.AccountFacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.AuthUnauthorizedException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

import static pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account.EMAIL_CONSTRAINT;
import static pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account.LOGIN_CONSTRAINT;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "ssbd03mokPU")
    private EntityManager em;

    public AccountFacade() {
        super(Account.class);
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

    @Override
    public List<Account> findAll() throws FacadeException {
        return super.findAll();
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


    public List<Account> getUnconfirmedAccounts() {
        TypedQuery<Account> tqq = em.createNamedQuery("Account.findUnconfirmedAccounts", Account.class);
        return tqq.getResultList();

    }

    @Override
    public void edit(Account entity) throws FacadeException {
        try {
            super.edit(entity);
        } catch (ConstraintViolationException e) {
            switch (e.getConstraintName()) {
                case LOGIN_CONSTRAINT:
                    throw AccountFacadeException.loginReserved(e);
                case EMAIL_CONSTRAINT:
                    throw AccountFacadeException.emailReserved(e);
            }
        }
    }

    @Override
    public void create(Account entity) throws FacadeException {
        super.create(entity);
    }

    public Account updateAuthenticateInfo(String login, String ipAddr, LocalDateTime time, boolean isAuthValid) throws AuthUnauthorizedException {
        TypedQuery<Account> tq = em.createNamedQuery("Account.findByLogin", Account.class);
        tq.setParameter("login", login);
        Account account;
        try {
            account = tq.getSingleResult();
            if (isAuthValid) {
                account.setLastCorrectAuthenticationDateTime(time);
                account.setLastCorrectAuthenticationLogicalAddress(ipAddr);
                account.setNumberOfAuthenticationFailures(account.getNumberOfAuthenticationFailures() + 1);
            } else {
                account.setLastIncorrectAuthenticationDateTime(time);
                account.setLastIncorrectAuthenticationLogicalAddress(ipAddr);
            }

        } catch (NoResultException e) {
            throw new AuthUnauthorizedException(I18n.INCORRECT_LOGIN);
        }

        return account;
    }
}
