package pl.lodz.p.it.ssbd2021.ssbd03.mok.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.wrappers.LanguageTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.AccountBlockedOrNotConfirmed;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.time.LocalDateTime;

@Stateless
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

    public Account updateAuthenticateInfo(String login, String ipAddr, LocalDateTime time, boolean isAuthValid) throws Exception {
        TypedQuery<Account> tq = em.createNamedQuery("Account.findByLogin", Account.class);
        tq.setParameter("login", login);
        Account account = tq.getSingleResult();

        if (!account.isActive() || !account.isConfirmed()) {
            throw new AccountBlockedOrNotConfirmed();
        }

        if (isAuthValid) {
            account.setLastCorrectAuthenticationDateTime(time);
            account.setLastCorrectAuthenticationLogicalAddress(ipAddr);
        } else {
            account.setLastIncorrectAuthenticationDateTime(time);
            account.setLastIncorrectAuthenticationLogicalAddress(ipAddr);
        }
        try {
            super.edit(account);
        } catch (Exception e) {
            throw new Exception(e);
        }

        return account;
    }
}
