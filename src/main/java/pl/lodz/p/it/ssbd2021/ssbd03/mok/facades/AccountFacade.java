package pl.lodz.p.it.ssbd2021.ssbd03.mok.facades;

import javassist.compiler.ast.Pair;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.wrappers.LanguageTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AuthenticateResponse;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

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

    public AuthenticateResponse authenticate(@NotNull String login, @NotNull String passwordHash) {
        TypedQuery<Account> tq = em.createNamedQuery("Account.findByLogin", Account.class);
        tq.setParameter("login", login);
        Account account = tq.getSingleResult();

        AuthenticateResponse ar = new AuthenticateResponse();
        ar.setAccount(account);
        if (account.getPasswordHash().equals(passwordHash)) {
            if (!account.isActive() || !account.isConfirmed()) {
                ar.setResponseStatus(Response.Status.FORBIDDEN);
            } else {
                ar.setResponseStatus(Response.Status.OK);
            }
        } else {
            ar.setResponseStatus(Response.Status.UNAUTHORIZED);
        }

        return ar;
    }
}
