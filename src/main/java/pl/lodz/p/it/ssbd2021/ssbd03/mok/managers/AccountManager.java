package pl.lodz.p.it.ssbd2021.ssbd03.mok.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Administrator;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Moderator;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.AccountManagerException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.CompanyFacadeMok;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

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
    public Account getAccountByLogin(String login) throws BaseAppException {
        return accountFacade.findByLogin(login);
    }

    @Override
    public void createClientAccount(Account account, Client client) {
        AlterTypeWrapper insertAlterType = accountFacade.getAlterTypeWrapperByAlterType(AlterType.INSERT);

        setAccessLevelInitialMetadata(account, client, true);

        client.getHomeAddress().setCreatedBy(account);
        client.getHomeAddress().setAlteredBy(account);
        client.getHomeAddress().setAlterType(insertAlterType);

        accountFacade.create(account);

    }

    @Override
    public void createBusinessWorkerAccount(Account account, BusinessWorker businessWorker, String companyName) {

        setAccessLevelInitialMetadata(account, businessWorker, true);
        businessWorker.setCompany(companyFacadeMok.getCompanyByName(companyName));

        this.accountFacade.create(account);
    }

    @Override
    public Account grantModeratorAccessLevel(String accountLogin, Long accountVersion) throws BaseAppException {
        Account account;
        try {
            account = accountFacade.findByLogin(accountLogin);
            account.setVersion(accountVersion); //to assure optimistic lock mechanism
            if (account.getAccessLevels().stream().anyMatch(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.MODERATOR)) {
                throw new AccountManagerException(ACCESS_LEVEL_ALREADY_ASSIGNED_ERROR);
            }
        } catch (FacadeException e) {
            throw new AccountManagerException(e.getMessage());
        }

        Moderator moderator = new Moderator(true);
        setAccessLevelInitialMetadata(account, moderator, false);

        return account;
    }

    @Override
    public Account grantAdministratorAccessLevel(String accountLogin, Long accountVersion) throws BaseAppException {
        Account account = accountFacade.findByLogin(accountLogin);
        account.setVersion(accountVersion); //to assure optimistic lock mechanism

        if (account.getAccessLevels().stream().anyMatch(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.ADMINISTRATOR)) {
            throw new AccountManagerException(ACCESS_LEVEL_ALREADY_ASSIGNED_ERROR);
        }

        Administrator administrator = new Administrator(true);
        setAccessLevelInitialMetadata(account, administrator, false);

        return account;
    }

    @Override
    public Account changeAccessLevelState(String accountLogin, AccessLevelType accessLevelType,
                                          boolean enabled, Long accountVersion) throws BaseAppException {
        Account account = accountFacade.findByLogin(accountLogin);
        account.setVersion(accountVersion); //to assure optimistic lock mechanism

        Optional<AccessLevel> accountAccessLevel = account.getAccessLevels().stream()
                .filter(accessLevel -> accessLevel.getAccessLevelType() == accessLevelType).findFirst();

        if (accountAccessLevel.isEmpty()) {
            throw new AccountManagerException(ACCESS_LEVEL_NOT_ASSIGNED_ERROR);
        }

        AccessLevel accessLevel = accountAccessLevel.get();

        if (enabled == accessLevel.isEnabled()) {
            throw new AccountManagerException(enabled ? ACCESS_LEVEL_ALREADY_ENABLED_ERROR : ACCESS_LEVEL_ALREADY_DISABLED_ERROR);
        }

        accessLevel.setEnabled(enabled);
        AlterTypeWrapper updateAlterType = accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE);
        account.setAlterType(updateAlterType);
        accessLevel.setAlterType(updateAlterType);
        return account;
    }


    private void setAccessLevelInitialMetadata(Account account, AccessLevel accessLevel, boolean newAccount) {

        AlterTypeWrapper insertAlterType = accountFacade.getAlterTypeWrapperByAlterType(AlterType.INSERT);
        AlterTypeWrapper updateAlterType = accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE);

        accessLevel.setAlteredBy(account);
        accessLevel.setCreatedBy(account);
        accessLevel.setAlterType(insertAlterType);
        accessLevel.setAccount(account);

        account.setAccessLevel(accessLevel);

        // alters Account properties depending of weather that is newly created or modified account
        if (newAccount) {
            account.setAlterType(insertAlterType);
            account.setLanguageType(accountFacade.getLanguageTypeWrapperByLanguageType(account.getLanguageType().getName()));
            account.setCreatedBy(account);
        } else {
            account.setAlterType(updateAlterType);
            account.setLastAlterDateTime(LocalDateTime.now());
        }
        account.setAlteredBy(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountFacade.findAll();
    }

    @Override
    public void blockUser(long id) {
        this.accountFacade.blockUser(id);
    }


    private AccessLevel getAccessLevel(Account from, AccessLevelType target) {
        return from.getAccessLevels().stream().filter(accessLevel -> accessLevel.getAccessLevelType().equals(target)).collect(Collectors.toList()).get(0);
    }

    private <T> T getAccessLevel(Account from) {
        return (T) new ArrayList<>(from.getAccessLevels()).get(0);
    }


    @Override
    public Account changeOtherClientData(Account fromAccount, String alterBy) throws BaseAppException {
        Account targetAccount = accountFacade.findByLogin(fromAccount.getLogin());

        targetAccount.setVersion(fromAccount.getVersion());
        targetAccount.setFirstName(fromAccount.getFirstName());
        targetAccount.setSecondName(fromAccount.getSecondName());
        targetAccount.setEmail(fromAccount.getEmail());
        targetAccount.setAlteredBy(accountFacade.findByLogin(alterBy));
        targetAccount.setAlterType(accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE));
        targetAccount.setLastAlterDateTime(LocalDateTime.now());

        Client targetClient = (Client) getAccessLevel(targetAccount, AccessLevelType.CLIENT);
        targetClient.setVersion(targetAccount.getVersion());
        targetClient.setAlteredBy(accountFacade.findByLogin(alterBy));
        targetClient.setAlterType(accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE));
        targetClient.setLastAlterDateTime(LocalDateTime.now());

        Client fromClient = getAccessLevel(fromAccount);

        targetClient.setPhoneNumber(fromClient.getPhoneNumber());

        Address targetAddress = targetClient.getHomeAddress();
        Address fromAddress = fromClient.getHomeAddress();
        targetAddress.setHouseNumber(fromAddress.getHouseNumber());
        targetAddress.setStreet(fromAddress.getStreet());
        targetAddress.setPostalCode(fromAddress.getPostalCode());
        targetAddress.setCity(fromAddress.getCity());
        targetAddress.setCountry(fromAddress.getCountry());
        targetAddress.setAlteredBy(accountFacade.findByLogin(alterBy));
        targetAddress.setAlterType(accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE));
        targetAddress.setLastAlterDateTime(LocalDateTime.now());
        return targetAccount;
    }

    @Override
    public Account changeOtherBusinessWorkerData(Account fromAccount, String alterBy) throws BaseAppException {
        Account targetAccount = accountFacade.findByLogin(fromAccount.getLogin());
        targetAccount.setVersion(fromAccount.getVersion());
        targetAccount.setFirstName(fromAccount.getFirstName());
        targetAccount.setSecondName(fromAccount.getSecondName());
        targetAccount.setEmail(fromAccount.getEmail());
        targetAccount.setAlteredBy(accountFacade.findByLogin(alterBy));
        targetAccount.setAlterType(accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE));
        targetAccount.setLastAlterDateTime(LocalDateTime.now());
        BusinessWorker targetBusinessWorker = (BusinessWorker) getAccessLevel(targetAccount, AccessLevelType.BUSINESS_WORKER);
        targetBusinessWorker.setVersion(targetAccount.getVersion());
        targetBusinessWorker.setAlteredBy(accountFacade.findByLogin(alterBy));
        targetBusinessWorker.setAlterType(accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE));
        targetBusinessWorker.setLastAlterDateTime(LocalDateTime.now());

        BusinessWorker fromBusinessWorker = getAccessLevel(fromAccount);
        targetBusinessWorker.setPhoneNumber(fromBusinessWorker.getPhoneNumber());
        return targetAccount;
    }

    @Override
    public Account changeOtherAccountData(Account fromAccount, String alterBy) throws BaseAppException {
        Account targetAccount = accountFacade.findByLogin(fromAccount.getLogin());
        targetAccount.setVersion(fromAccount.getVersion());
        targetAccount.setFirstName(fromAccount.getFirstName());
        targetAccount.setSecondName(fromAccount.getSecondName());
        targetAccount.setEmail(fromAccount.getEmail());
        targetAccount.setAlteredBy(accountFacade.findByLogin(alterBy));
        targetAccount.setAlterType(accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE));
        targetAccount.setLastAlterDateTime(LocalDateTime.now());
        return targetAccount;
    }


}
