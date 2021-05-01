package pl.lodz.p.it.ssbd2021.ssbd03.mok.managers;

import org.apache.commons.codec.digest.DigestUtils;
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
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.converters.AccountMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.CompanyFacade;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Klasa która zarządza logiką biznesową kont
 */
@Stateful
public class AccountManager implements AccountManagerLocal {

    @EJB
    private AccountFacade accountFacade;

    @EJB
    private CompanyFacade companyFacade;

    @Override
    public void createClientAccount(Account account, Client client, Address address) {
        AlterTypeWrapper insertAlterType = accountFacade.getAlterTypeWrapperByAlterType(AlterType.INSERT);

        account.setPasswordHash(DigestUtils.sha256Hex(account.getPasswordHash()));
        account.setLanguageType(accountFacade.getLanguageTypeWrapperByLanguageType(account.getLanguageType().getName()));
        account.setAlterType(insertAlterType);
        account.setAlteredBy(account);
        account.setCreatedBy(account);

        client.setAlteredBy(account);
        client.setCreatedBy(account);
        client.setAlterType(insertAlterType);
        client.setAccount(account);

        address.setCreatedBy(account);
        address.setAlteredBy(account);
        address.setAlterType(insertAlterType);

        client.setHomeAddress(address);

        account.setAccessLevel(client);

        accountFacade.create(account);
    }

    @Override
    public void createBusinessWorker(Account account, BusinessWorker businessWorker, String companyName) {
        AlterTypeWrapper insertAlterType = accountFacade.getAlterTypeWrapperByAlterType(AlterType.INSERT);

        account.setPasswordHash(DigestUtils.sha256Hex(account.getPasswordHash()));
        account.setLanguageType(accountFacade.getLanguageTypeWrapperByLanguageType(account.getLanguageType().getName()));
        account.setAlterType(insertAlterType);
        account.setAlteredBy(account);
        account.setCreatedBy(account);

        businessWorker.setAlteredBy(account);
        businessWorker.setCreatedBy(account);
        businessWorker.setAlterType(insertAlterType);
        businessWorker.setAccount(account);
        businessWorker.setCompany(companyFacade.getCompanyByName(companyName));

        account.setAccessLevel(businessWorker);

        this.accountFacade.create(account);
    }

    @Override
    public void createAdministrator(Account account, Administrator administrator) {
        AlterTypeWrapper insertAlterType = accountFacade.getAlterTypeWrapperByAlterType(AlterType.INSERT);

        account.setPasswordHash(DigestUtils.sha256Hex(account.getPasswordHash()));
        account.setLanguageType(accountFacade.getLanguageTypeWrapperByLanguageType(account.getLanguageType().getName()));
        account.setAlterType(insertAlterType);
        account.setAlteredBy(account);
        account.setCreatedBy(account);

        administrator.setAlteredBy(account);
        administrator.setCreatedBy(account);
        administrator.setAlterType(insertAlterType);
        administrator.setAccount(account);

        account.setAccessLevel(administrator);

        this.accountFacade.create(account);
    }

    @Override
    public void createModerator(Account account, Moderator moderator) {
        AlterTypeWrapper insertAlterType = accountFacade.getAlterTypeWrapperByAlterType(AlterType.INSERT);

        account.setPasswordHash(DigestUtils.sha256Hex(account.getPasswordHash()));
        account.setLanguageType(accountFacade.getLanguageTypeWrapperByLanguageType(account.getLanguageType().getName()));
        account.setAlterType(insertAlterType);
        account.setAlteredBy(account);
        account.setCreatedBy(account);

        moderator.setAlteredBy(account);
        moderator.setCreatedBy(account);
        moderator.setAlterType(insertAlterType);
        moderator.setAccount(account);

        account.setAccessLevel(moderator);

        this.accountFacade.create(account);
    }

    @Override
    public void changeEmail(String login, Long version, String newEmail) {
        Account account = accountFacade.findByLogin(login);
        account.setVersion(version);
        account.setEmail(newEmail);
    }

    private AccessLevel getAccessLevel(Account from, AccessLevelType target) {
        System.out.println("SIZE: " + from.getAccessLevels().size());
        return from.getAccessLevels().stream().filter(accessLevel -> accessLevel.getAccessLevelType().equals(target)).collect(Collectors.toList()).get(0);
    }

    private <T> T getAccessLevel(Account from) {
        return (T) new ArrayList<>(from.getAccessLevels()).get(0);
    }

    private void setAccountChanges(Account target, Account from) {
        target.setVersion(from.getVersion());
        target.setFirstName(from.getFirstName());
        target.setSecondName(from.getSecondName());
        target.setAlteredBy(target);
        target.setAlterType(target.getAlterType());
        target.setLastAlterDateTime(from.getLastAlterDateTime());
    }

    private void setAccessLevelChanges(AccessLevel target, Account from) {
        target.setVersion(from.getVersion());
        target.setAlteredBy(from);
        target.setAlterType(from.getAlterType());
        target.setLastAlterDateTime(from.getLastAlterDateTime());
    }

    @Override
    public void changeClientData(Account fromAccount) {
        Account targetAccount = accountFacade.findByLogin(fromAccount.getLogin());
        setAccountChanges(targetAccount, fromAccount);

        Client targetClient = (Client) getAccessLevel(targetAccount, AccessLevelType.CLIENT);
        setAccessLevelChanges(targetClient, targetAccount);

        Client fromClient = getAccessLevel(fromAccount);

        targetClient.setPhoneNumber(fromClient.getPhoneNumber());

        Address targetAddress = targetClient.getHomeAddress();
        Address fromAddress = fromClient.getHomeAddress();
        targetAddress.setHouseNumber(fromAddress.getHouseNumber());
        targetAddress.setStreet(fromAddress.getStreet());
        targetAddress.setPostalCode(fromAddress.getPostalCode());
        targetAddress.setCity(fromAddress.getCity());
        targetAddress.setCountry(fromAddress.getCountry());
        targetAddress.setAlteredBy(targetAccount);
        targetAddress.setAlterType(targetAccount.getAlterType());
        targetAddress.setLastAlterDateTime(fromAccount.getLastAlterDateTime());
    }

    @Override
    public void changeBusinessWorkerData(Account fromAccount) {
        Account targetAccount = accountFacade.findByLogin(fromAccount.getLogin());
        setAccountChanges(targetAccount, fromAccount);

        BusinessWorker targetBusinessWorker = (BusinessWorker) getAccessLevel(targetAccount, AccessLevelType.BUSINESS_WORKER);
        setAccessLevelChanges(targetBusinessWorker, targetAccount);

        BusinessWorker fromBusinessWorker = getAccessLevel(fromAccount);
        targetBusinessWorker.setPhoneNumber(fromBusinessWorker.getPhoneNumber());
    }

    @Override
    public void changeModeratorData(Account fromAccount) {
        Account targetAccount = accountFacade.findByLogin(fromAccount.getLogin());
        setAccountChanges(targetAccount, fromAccount);

        Moderator targetModerator = (Moderator) getAccessLevel(targetAccount, AccessLevelType.MODERATOR);
        setAccessLevelChanges(targetModerator, targetAccount);
    }

    @Override
    public void changeAdministratorData(Account fromAccount) {
        Account targetAccount = accountFacade.findByLogin(fromAccount.getLogin());

        setAccountChanges(targetAccount, fromAccount);

        Administrator targetAdministrator = (Administrator) getAccessLevel(targetAccount, AccessLevelType.ADMINISTRATOR);

        setAccessLevelChanges(targetAdministrator, targetAccount);
    }
}
