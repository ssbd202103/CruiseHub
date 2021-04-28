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
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountChangeEmailDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.AddressChangeDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.CompanyFacade;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateful;
import java.time.LocalDateTime;
import java.util.Arrays;
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

    private void setAccountChanges(Account account,Long version, String newFirstName, String newSecondName, LocalDateTime time) {
        account.setVersion(version);
        account.setFirstName(newFirstName);
        account.setSecondName(newSecondName);
        account.setAlteredBy(account);
        account.setAlterType(account.getAlterType());
        account.setLastAlterDateTime(time);
    }

    private void setAccessLevelChanges(AccessLevel accessLevel, Account account, Long version, LocalDateTime time) {
        accessLevel.setVersion(version);
        accessLevel.setAlteredBy(account);
        accessLevel.setAlterType(account.getAlterType());
        accessLevel.setLastAlterDateTime(time);
    }

    @Override
    public void changeClientData(String login, Long version,
                                 String newFirstName, String newSecondName, String newPhoneNumber,
                                 Long newHouseNumber, String newStreet, String newPostalCode, String newCity,String newCountry) {
        LocalDateTime time = LocalDateTime.now();

        Account account = accountFacade.findByLogin(login);

        setAccountChanges(account, version, newFirstName, newSecondName, time);

        Client client = (Client) getAccessLevel(account, AccessLevelType.CLIENT);

        setAccessLevelChanges(client, account, version, time);

        client.setPhoneNumber(newPhoneNumber);

        Address address = client.getHomeAddress();
        address.setHouseNumber(newHouseNumber);
        address.setStreet(newStreet);
        address.setPostalCode(newPostalCode);
        address.setCity(newCity);
        address.setCountry(newCountry);
        address.setAlteredBy(account);
        address.setAlterType(account.getAlterType());
        address.setLastAlterDateTime(time);
    }

    @Override
    public void changeBusinessWorkerData(String login, Long version, String newFirstName, String newSecondName, String newPhoneNumber) {
        LocalDateTime time = LocalDateTime.now();

        Account account = accountFacade.findByLogin(login);

        setAccountChanges(account, version, newFirstName, newSecondName, time);

        BusinessWorker businessWorker = (BusinessWorker) getAccessLevel(account, AccessLevelType.BUSINESS_WORKER);

        setAccessLevelChanges(businessWorker, account, version, time);

        businessWorker.setPhoneNumber(newPhoneNumber);
    }

    @Override
    public void changeModeratorData(String login, Long version, String newFirstName, String newSecondName) {
        LocalDateTime time = LocalDateTime.now();

        Account account = accountFacade.findByLogin(login);

        setAccountChanges(account, version, newFirstName, newSecondName, time);

        Moderator moderator = (Moderator) getAccessLevel(account, AccessLevelType.MODERATOR);

        setAccessLevelChanges(moderator, account, version, time);
    }

    @Override
    public void changeAdministratorData(String login, Long version, String newFirstName, String newSecondName) {
        LocalDateTime time = LocalDateTime.now();

        Account account = accountFacade.findByLogin(login);

        setAccountChanges(account, version, newFirstName, newSecondName, time);

        Administrator administrator = (Administrator) getAccessLevel(account, AccessLevelType.ADMINISTRATOR);

        setAccessLevelChanges(administrator, account, version, time);
    }
}
