package pl.lodz.p.it.ssbd2021.ssbd03.mok.managers;

import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Administrator;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Moderator;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.CompanyFacadeMok;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa która zarządza logiką biznesową kont
 */
@Stateful
public class AccountManager implements AccountManagerLocal {

    @EJB
    private AccountFacade accountFacade;

    @EJB
    private CompanyFacadeMok companyFacadeMok;

    @Override
    public void createClientAccount(Account account, Client client) {
        AlterTypeWrapper insertAlterType = accountFacade.getAlterTypeWrapperByAlterType(AlterType.INSERT);

        account.setLanguageType(accountFacade.getLanguageTypeWrapperByLanguageType(account.getLanguageType().getName()));
        account.setAlterType(insertAlterType);
        account.setAlteredBy(account);
        account.setCreatedBy(account);

        client.setAlteredBy(account);
        client.setCreatedBy(account);
        client.setAlterType(insertAlterType);
        client.setAccount(account);

        client.getHomeAddress().setCreatedBy(account);
        client.getHomeAddress().setAlteredBy(account);
        client.getHomeAddress().setAlterType(insertAlterType);


        account.setAccessLevel(client);

        accountFacade.create(account);

    }

    @Override
    public void createBusinessWorker(Account account, BusinessWorker businessWorker, String companyName) {
        AlterTypeWrapper insertAlterType = accountFacade.getAlterTypeWrapperByAlterType(AlterType.INSERT);

        account.setLanguageType(accountFacade.getLanguageTypeWrapperByLanguageType(account.getLanguageType().getName()));
        account.setAlterType(insertAlterType);
        account.setAlteredBy(account);
        account.setCreatedBy(account);

        businessWorker.setAlteredBy(account);
        businessWorker.setCreatedBy(account);
        businessWorker.setAlterType(insertAlterType);
        businessWorker.setAccount(account);
        businessWorker.setCompany(companyFacadeMok.getCompanyByName(companyName));

        account.setAccessLevel(businessWorker);

        this.accountFacade.create(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountFacade.findAll();
    }

}
