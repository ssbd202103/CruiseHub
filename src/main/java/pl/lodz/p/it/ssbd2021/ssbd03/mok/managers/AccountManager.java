package pl.lodz.p.it.ssbd2021.ssbd03.mok.managers;

import com.auth0.jwt.interfaces.Claim;
import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;
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
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.converters.AccountMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.CompanyFacadeMok;
import pl.lodz.p.it.ssbd2021.ssbd03.security.JWTHandler;
import pl.lodz.p.it.ssbd2021.ssbd03.services.EmailService;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

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

    @Inject
    I18n i18n;

    @Override
    public void createClientAccount(Account account, Client client) throws BaseAppException {
        AlterTypeWrapper insertAlterType = accountFacade.getAlterTypeWrapperByAlterType(AlterType.INSERT);

        setAccessLevelInitialMetadata(account, client, true);

        client.getHomeAddress().setCreatedBy(account);
        client.getHomeAddress().setAlteredBy(account);
        client.getHomeAddress().setAlterType(insertAlterType);

        accountFacade.create(account);
        if (accountFacade.findByLogin(account.getLogin()) != null) {
            sendVerificationEmail(account);
        }
    }

    @Override
    public void createBusinessWorkerAccount(Account account, BusinessWorker businessWorker, String companyName) throws BaseAppException {

        setAccessLevelInitialMetadata(account, businessWorker, true);
        businessWorker.setCompany(companyFacadeMok.getCompanyByName(companyName));

        this.accountFacade.create(account);
        if (accountFacade.findByLogin(account.getLogin()) != null) {
            sendVerificationEmail(account);
        }
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

    private void sendVerificationEmail(Account account) throws BaseAppException {
        Map<String, Object> claims = Map.of("version", account.getVersion());
        String token = JWTHandler.createTokenEmail(claims, account.getLogin());
        Locale locale = new Locale(account.getLanguageType().getName().name());
        String subject = i18n.getMessage(VERIFICATION_EMAIL_SUBJECT, locale);
        String body = i18n.getMessage(VERIFICATION_EMAIL_BODY, locale);
        String contentHtml = "<a href=\"" + PropertiesReader.getSecurityProperties().getProperty("app.baseurl") + "/verify/accountVerification/" + token + "\">" + body + "</a>";
        EmailService.sendEmailWithContent(account.getEmail().trim(), subject, contentHtml);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountFacade.findAll();
    }

    @Override
    public Account blockUser(String login, Long version) throws BaseAppException {
        Account account = this.accountFacade.findByLogin(login);
        account.setVersion(version);
        account.setActive(false);
        setAlterTypeAndAlterAccount(account, accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE),
                // this is for now, will be changed in the upcoming feature
                account);
        accountFacade.edit(account);
        return account;
    }


    @Override
    public void requestPasswordReset(String login) throws BaseAppException {
        Account account = this.accountFacade.findByLogin(login);
        Map<String, Object> claims = Map.of("version", account.getVersion());
        String token = JWTHandler.createToken(claims, login);
        String contentHtml = "<a href=\"" + PropertiesReader.getSecurityProperties().getProperty("app.baseurl") + "/reset/passwordReset/" + token + "\">Reset password</a>";
        EmailService.sendEmailWithContent(account.getEmail().trim(), "hello", contentHtml);
    }

    @Override
    public void resetPassword(String login, String passwordHash, String token) throws BaseAppException {
        JWTHandler.validateToken(token);

        Map<String, Claim> claims = JWTHandler.getClaimsFromToken(token);

        if (claims.get("sub") != null && claims.get("version") != null) {
            if (claims.get("sub").asString().equals(login)) {
                Account account = this.accountFacade.findByLogin(login);
                account.setPasswordHash(passwordHash);
                account.setVersion(claims.get("version").asLong());
                account.setAlterType(accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE));
                account.setAlteredBy(account);
            } else {
                throw new AccountManagerException(PASSWORD_RESET_IDENTITY_ERROR);
            }
        } else {
            throw new AccountManagerException(PASSWORD_RESET_TOKEN_CONTENT_ERROR);
        }

    }

    @Override
    public void verifyAccount(String token) throws BaseAppException {
        JWTHandler.validateToken(token);

        Map<String, Claim> claims = JWTHandler.getClaimsFromToken(token);
        Date expire = JWTHandler.getExpiersTimeFromToken(token);
        if (claims.get("sub") != null && claims.get("version") != null) {
            String login = claims.get("sub").asString();
            if (!expire.before(new Date(System.currentTimeMillis()))) {
                Account account = this.accountFacade.findByLogin(login);
                if (!account.isConfirmed()) {
                    account.setConfirmed(true);
                    account.setVersion(claims.get("version").asLong());
                    account.setAlterType(accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE));
                    account.setAlteredBy(account);
                    Locale locale = new Locale(account.getLanguageType().getName().name());
                    String body = i18n.getMessage(ACTIVATE_ACCOUNT_BODY, locale);
                    String subject = i18n.getMessage(ACTIVATE_ACCOUNT_SUBJECT, locale);
                    EmailService.sendEmailWithContent(account.getEmail().trim(), subject, body);
                } else {
                    throw new AccountManagerException(ACCOUNT_VERIFICATION_TOKEN_ALREADY_VERIFIED_ERROR);
                }
            } else {
                throw new AccountManagerException(ACCOUNT_VERIFICATION_TOKEN_EXPIRE_ERROR);
            }
        } else {
            throw new AccountManagerException(ACCOUNT_VERIFICATION_TOKEN_CONTENT_ERROR);
        }
    }

    @Override
    public void requestSomeonesPasswordReset(String login, String email) throws BaseAppException {
        Account account = this.accountFacade.findByLogin(login);
        Map<String, Object> claims = Map.of("version", account.getVersion());
        Locale locale = new Locale(account.getLanguageType().getName().name());
        String token = JWTHandler.createToken(claims, login);
        String subject = i18n.getMessage(REQUESTED_PASSWORD_RESET_SUBJECT, locale);
        String body = i18n.getMessage(REQUESTED_PASSWORD_RESET_BODY, locale);
        String contentHtml = "<a href=\"" + PropertiesReader.getSecurityProperties().getProperty("app.baseurl") + "/reset/passwordReset/" + token + "\">" + body + "</a>";
        EmailService.sendEmailWithContent(email, subject, contentHtml);
    }

    @Override
    public Account unblockUser(String unblockedUserLogin, Long version) throws BaseAppException {
        Account account = this.accountFacade.findByLogin(unblockedUserLogin);
        account.setVersion(version);
        account.setActive(true);
        setAlterTypeAndAlterAccount(account, accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE),
                //needs to be changed as in blockUser method
                account);
        return account;
    }

    private void setAlterTypeAndAlterAccount(Account account, AlterTypeWrapper alterTypeWrapper, Account alteredBy) {
        account.setAlteredBy(alteredBy);
        account.setAlterType(alterTypeWrapper);
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
        Account targetAccount = updateAccount(fromAccount, alterBy);

        Client targetClient = (Client) getAccessLevel(targetAccount, AccessLevelType.CLIENT);
        targetClient.setVersion(targetAccount.getVersion());
        targetClient.setAlteredBy(accountFacade.findByLogin(alterBy));
        targetClient.setAlterType(accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE));
        targetClient.setLastAlterDateTime(LocalDateTime.now());

        Client fromClient = (Client) getAccessLevel(fromAccount, AccessLevelType.CLIENT);
        updateClient(fromClient, targetClient);

        Address targetAddress = targetClient.getHomeAddress();
        targetAddress.setAlteredBy(accountFacade.findByLogin(alterBy));
        targetAddress.setAlterType(accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE));
        targetAddress.setLastAlterDateTime(LocalDateTime.now());
        return targetAccount;
    }

    private void updateClient(Client fromClient, Client targetClient) {
        targetClient.setPhoneNumber(fromClient.getPhoneNumber());

        Address targetAddress = targetClient.getHomeAddress();
        Address fromAddress = fromClient.getHomeAddress();
        targetAddress.setHouseNumber(fromAddress.getHouseNumber());
        targetAddress.setStreet(fromAddress.getStreet());
        targetAddress.setPostalCode(fromAddress.getPostalCode());
        targetAddress.setCity(fromAddress.getCity());
        targetAddress.setCountry(fromAddress.getCountry());
    }

    private Account updateAccount(Account updatedAccount, String alteredBy) throws BaseAppException {
        Account targetAccount = accountFacade.findByLogin(updatedAccount.getLogin());

        targetAccount.setVersion(updatedAccount.getVersion());
        targetAccount.setFirstName(updatedAccount.getFirstName());
        targetAccount.setSecondName(updatedAccount.getSecondName());
        targetAccount.setAlteredBy(accountFacade.findByLogin(alteredBy));
        targetAccount.setAlterType(accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE));
        targetAccount.setLastAlterDateTime(LocalDateTime.now());
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
        Account targetAccount = updateAccount(fromAccount, alterBy);
        BusinessWorker targetBusinessWorker = (BusinessWorker) getAccessLevel(targetAccount, AccessLevelType.BUSINESS_WORKER);
        targetBusinessWorker.setVersion(targetAccount.getVersion());
        targetBusinessWorker.setAlteredBy(accountFacade.findByLogin(alterBy));
        targetBusinessWorker.setAlterType(accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE));
        targetBusinessWorker.setLastAlterDateTime(LocalDateTime.now());

        BusinessWorker fromBusinessWorker = (BusinessWorker) getAccessLevel(fromAccount, AccessLevelType.BUSINESS_WORKER);
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

    @Override
    public void changeEmail(String login, Long version, String newEmail) throws BaseAppException {
        Account account = accountFacade.findByLogin(login);
        account.setVersion(version);
        account.setEmail(newEmail);

        account.setLastAlterDateTime(LocalDateTime.now());
        account.setAlteredBy(account);
        account.setAlterType(account.getAlterType());
    }

    private AccessLevel getAccessLevel(Account from, AccessLevelType target) throws AccountManagerException {
        Optional<AccessLevel> optionalAccessLevel = from.getAccessLevels().stream()
                .filter(accessLevel -> accessLevel.getAccessLevelType().equals(target)).findAny();

        if (optionalAccessLevel.isEmpty()) {
            throw new AccountManagerException(ACCESS_LEVEL_DOES_NOT_EXIST_ERROR);
        }
        return optionalAccessLevel.get();
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
    public void changeClientData(Account fromAccount) throws BaseAppException {
        Account targetAccount = accountFacade.findByLogin(fromAccount.getLogin());
        setAccountChanges(targetAccount, fromAccount);

        Client targetClient = (Client) getAccessLevel(targetAccount, AccessLevelType.CLIENT);
        setAccessLevelChanges(targetClient, targetAccount);

        Client fromClient = (Client) getAccessLevel(fromAccount, AccessLevelType.CLIENT);
        updateClient(fromClient, targetClient);

        Address targetAddress = targetClient.getHomeAddress();
        targetAddress.setAlteredBy(targetAccount);
        targetAddress.setAlterType(targetAccount.getAlterType());
        targetAddress.setLastAlterDateTime(fromAccount.getLastAlterDateTime());
    }

    @Override
    public void changeBusinessWorkerData(Account fromAccount) throws BaseAppException {
        Account targetAccount = accountFacade.findByLogin(fromAccount.getLogin());
        setAccountChanges(targetAccount, fromAccount);

        BusinessWorker targetBusinessWorker = (BusinessWorker) getAccessLevel(targetAccount, AccessLevelType.BUSINESS_WORKER);
        setAccessLevelChanges(targetBusinessWorker, targetAccount);

        BusinessWorker fromBusinessWorker = (BusinessWorker) getAccessLevel(fromAccount, AccessLevelType.BUSINESS_WORKER);
        targetBusinessWorker.setPhoneNumber(fromBusinessWorker.getPhoneNumber());
    }

    @Override
    public void updateIncorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time) throws AuthUnauthorizedException {
        this.accountFacade.updateAuthenticateInfo(login, IpAddr, time, false);
    }


    @Override
    public void changeModeratorData(Account fromAccount) throws BaseAppException {
        Account targetAccount = accountFacade.findByLogin(fromAccount.getLogin());
        setAccountChanges(targetAccount, fromAccount);

        Moderator targetModerator = (Moderator) getAccessLevel(targetAccount, AccessLevelType.MODERATOR);
        setAccessLevelChanges(targetModerator, targetAccount);
    }

    @Override
    public void changeAdministratorData(Account fromAccount) throws BaseAppException {
        Account targetAccount = accountFacade.findByLogin(fromAccount.getLogin());

        setAccountChanges(targetAccount, fromAccount);

        Administrator targetAdministrator = (Administrator) getAccessLevel(targetAccount, AccessLevelType.ADMINISTRATOR);

        setAccessLevelChanges(targetAdministrator, targetAccount);
    }

    @Override
    public Account getAccountByLogin(String login) throws BaseAppException {
        return accountFacade.findByLogin(login);
    }

    @Override
    public String updateCorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time) throws AuthUnauthorizedException {
        Account account = this.accountFacade.updateAuthenticateInfo(login, IpAddr, time, true);

        Map<String, Object> map = Map.of("login", login, "accessLevels", account.getAccessLevels()
                .stream().map(accessLevel -> accessLevel.getAccessLevelType().name()).collect(Collectors.toList()));
        return JWTHandler.createToken(map, account.getId().toString());
    }
}
