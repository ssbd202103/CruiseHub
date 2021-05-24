package pl.lodz.p.it.ssbd2021.ssbd03.mok.managers;

import com.auth0.jwt.interfaces.Claim;
import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.TokenWrapper;
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
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AccountFacadeMok;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.CompanyFacadeMok;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.TokenWrapperFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.security.JWTHandler;
import pl.lodz.p.it.ssbd2021.ssbd03.services.EmailService;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

/**
 * Klasa która zarządza logiką biznesową kont
 */

//@Stateless todo check why stateless throws exceptions occasionally
@Stateful
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@RunAs("SYSTEM")
@Interceptors(TrackingInterceptor.class)
public class AccountManager implements AccountManagerLocal {

    @Inject
    private AccountFacadeMok accountFacade;

    @Inject
    private CompanyFacadeMok companyFacadeMok;

    @Inject
    private TokenWrapperFacade tokenWrapperFacade;

    @Context
    private SecurityContext context;

    @Inject
    I18n i18n;


    private static final Properties securityProperties = PropertiesReader.getSecurityProperties();

    @PermitAll
    @Override
    public void createClientAccount(Account account, Client client) throws BaseAppException {
        account.setLanguageType(accountFacade.getLanguageTypeWrapperByLanguageType(account.getLanguageType().getName()));
        setAccessLevelInitialMetadata(account, client, true);
        Address address = client.getHomeAddress();

        setCreatedMetadata(account, account, address);
        accountFacade.create(account);

        //todo uncomment it when it's needed
        if (accountFacade.findByLogin(account.getLogin()) != null) {
            sendVerificationEmail(account);
        }
    }

    @PermitAll
    @Override
    public void createBusinessWorkerAccount(Account account, BusinessWorker businessWorker, String companyName) throws BaseAppException {
        account.setLanguageType(accountFacade.getLanguageTypeWrapperByLanguageType(account.getLanguageType().getName()));
        setAccessLevelInitialMetadata(account, businessWorker, true);
        businessWorker.setCompany(companyFacadeMok.getCompanyByName(companyName));

        setCreatedMetadata(account, account);
        accountFacade.create(account);
        //todo uncomment it when it's needed
        if (accountFacade.findByLogin(account.getLogin()) != null) {
            sendVerificationEmail(account);
        }
    }

    @RolesAllowed("grantAccessLevel")
    @Override
    public Account grantModeratorAccessLevel(String accountLogin, long accountVersion) throws BaseAppException {
        Account account = accountFacade.findByLogin(accountLogin);
        if (!(account.getVersion() == accountVersion)) {
            throw FacadeException.optimisticLock();
        }
        if (account.getAccessLevels().stream().anyMatch(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.MODERATOR)) {
            throw new AccountManagerException(ACCESS_LEVEL_ALREADY_ASSIGNED_ERROR);
        }

        Moderator moderator = new Moderator(true);
        setAccessLevelInitialMetadata(account, moderator, false);
        setUpdatedMetadata(account);
        return account;
    }

    @RolesAllowed("grantAccessLevel")
    @Override
    public Account grantAdministratorAccessLevel(String accountLogin, long accountVersion) throws BaseAppException {
        Account account = accountFacade.findByLogin(accountLogin);
        if (!(account.getVersion() == accountVersion)) {
            throw FacadeException.optimisticLock();
        }
        if (account.getAccessLevels().stream().anyMatch(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.ADMINISTRATOR)) {
            throw new AccountManagerException(ACCESS_LEVEL_ALREADY_ASSIGNED_ERROR);
        }

        Administrator administrator = new Administrator(true);
        setAccessLevelInitialMetadata(account, administrator, false);
        setUpdatedMetadata(account);
        return account;
    }

    @RolesAllowed("changeAccessLevelState")
    @Override
    public Account changeAccessLevelState(String accountLogin, AccessLevelType accessLevelType,
                                          boolean enabled, long accountVersion) throws BaseAppException {
        Account account = accountFacade.findByLogin(accountLogin);
        if (!(account.getVersion() == accountVersion)) {
            throw FacadeException.optimisticLock();
        }

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
        setUpdatedMetadata(account, accessLevel);
        return account;
    }


    private void setAccessLevelInitialMetadata(Account account, AccessLevel accessLevel, boolean newAccount) throws BaseAppException {
        setCreatedMetadata(newAccount ? account : getCurrentUser(), accessLevel);

        account.setAccessLevel(accessLevel);
        accessLevel.setAccount(account);
    }

    private void sendVerificationEmail(Account account) throws BaseAppException {
        Map<String, Object> claims = Map.of("version", account.getVersion());
        String token = JWTHandler.createTokenEmail(claims, account.getLogin());
        Locale locale = new Locale(account.getLanguageType().getName().name());
        String subject = i18n.getMessage(VERIFICATION_EMAIL_SUBJECT, locale);
        String body = i18n.getMessage(VERIFICATION_EMAIL_BODY, locale);
        TokenWrapper tokenWrapper = TokenWrapper.builder().token(token).account(account).used(false).build();
        this.tokenWrapperFacade.create(tokenWrapper);
        String contentHtml = "<a href=\"" + PropertiesReader.getSecurityProperties().getProperty("app.baseurl") + "/verify/accountVerification/" + token + "\">" + body + "</a>";
        EmailService.sendEmailWithContent(account.getEmail().trim(), subject, contentHtml);
    }

    @RolesAllowed("getAllAccounts")
    @Override
    public List<Account> getAllAccounts() throws BaseAppException {
        return accountFacade.findAll();
    }

    @RolesAllowed("getAllUnconfirmedBusinessWorkers")
    @Override
    public List<Account> getAllUnconfirmedBusinessWorkers() throws BaseAppException {
        List<Account> res = new ArrayList<>();
       for(AccessLevel account : accountFacade.getUnconfirmedBusinessWorkers()) {
               res.add(account.getAccount());
       }
       return res;
    }
    @RolesAllowed("blockUser")
    @Override
    public Account blockUser(String login, long version) throws BaseAppException {
        Account account = this.accountFacade.findByLogin(login);
        if (!(account.getVersion() == version)) {
            throw FacadeException.optimisticLock();
        }
        account.setActive(false);
        setUpdatedMetadata(account);
        accountFacade.edit(account);
        return account;
    }


    @PermitAll
    @Override
    public void requestPasswordReset(String login) throws BaseAppException {
        Account account = this.accountFacade.findByLogin(login);
        Map<String, Object> claims = Map.of("version", account.getVersion());
        String token = JWTHandler.createToken(claims, login);
        TokenWrapper tokenWrapper = TokenWrapper.builder().token(token).account(account).used(false).build();
        this.tokenWrapperFacade.create(tokenWrapper);
        Locale locale = new Locale(account.getLanguageType().getName().name());
        String subject = i18n.getMessage(REQUESTED_PASSWORD_RESET_SUBJECT, locale);
        String body = i18n.getMessage(REQUESTED_PASSWORD_RESET_BODY, locale);
        String contentHtml = "<a href=\"" + PropertiesReader.getSecurityProperties().getProperty("app.baseurl") + "/reset/passwordReset/" + token + "\">" + body + "</a>";
        EmailService.sendEmailWithContent(account.getEmail().trim(), subject, contentHtml);
    }

    @PermitAll
    @Override
    public void resetPassword(String login, String passwordHash, String token) throws BaseAppException {
        JWTHandler.validateToken(token);

        Map<String, Claim> claims = JWTHandler.getClaimsFromToken(token);

        if (claims.get("sub").isNull() || claims.get("version").isNull()) {
            throw new AccountManagerException(PASSWORD_RESET_TOKEN_CONTENT_ERROR);
        }
        if (!claims.get("sub").asString().equals(login)) {
            throw new AccountManagerException(PASSWORD_RESET_IDENTITY_ERROR);

        }

        Account account = this.accountFacade.findByLogin(login);
        if (!(account.getVersion() == claims.get("version").asLong())) {
            throw FacadeException.optimisticLock();
        }

        account.setPasswordHash(passwordHash);

        setUpdatedMetadata(account);
        this.accountFacade.edit(account);

        TokenWrapper tokenWrapper = this.tokenWrapperFacade.findByToken(token);
        tokenWrapper.setUsed(true);
        this.tokenWrapperFacade.edit(tokenWrapper);

    }

    @PermitAll
    @Override
    public void verifyAccount(String token) throws BaseAppException {
        JWTHandler.validateToken(token);

        Map<String, Claim> claims = JWTHandler.getClaimsFromToken(token);
        Date expire = JWTHandler.getExpirationTimeFromToken(token);

        if (claims.get("sub").isNull() || claims.get("version").isNull()) {
            throw new AccountManagerException(ACCOUNT_VERIFICATION_TOKEN_CONTENT_ERROR);
        }

        String login = claims.get("sub").asString();
        if (expire.before(new Date())) {
            throw new AccountManagerException(TOKEN_EXPIRED_ERROR);
        }

        Account account = this.accountFacade.findByLogin(login);
        if (account.isConfirmed()) {
            throw new AccountManagerException(ACCOUNT_VERIFICATION_TOKEN_ALREADY_VERIFIED_ERROR);
        }

        if (!(account.getVersion() == claims.get("version").asLong())) {
            throw FacadeException.optimisticLock();
        }

        account.setConfirmed(true);
        TokenWrapper tokenWrapper = this.tokenWrapperFacade.findByToken(token);
        tokenWrapper.setUsed(true);
        this.tokenWrapperFacade.edit(tokenWrapper);
        setUpdatedMetadataWithModifier(account, account);
        Locale locale = new Locale(account.getLanguageType().getName().name());
        String body = i18n.getMessage(ACTIVATE_ACCOUNT_BODY, locale);
        String subject = i18n.getMessage(ACTIVATE_ACCOUNT_SUBJECT, locale);
        EmailService.sendEmailWithContent(account.getEmail().trim(), subject, body);
    }

    @RolesAllowed("requestSomeonesPasswordReset")
    @Override
    public void requestSomeonesPasswordReset(String login, String email) throws BaseAppException {
        Account account = this.accountFacade.findByLogin(login);
        Map<String, Object> claims = Map.of("version", account.getVersion());
        Locale locale = new Locale(account.getLanguageType().getName().name());
        String token = JWTHandler.createToken(claims, login);
        String subject = i18n.getMessage(REQUESTED_PASSWORD_RESET_SUBJECT, locale);
        String body = i18n.getMessage(REQUESTED_PASSWORD_RESET_BODY, locale);
        TokenWrapper tokenWrapper = TokenWrapper.builder().token(token).account(account).used(false).build();
        this.tokenWrapperFacade.create(tokenWrapper);
        String contentHtml = "<a href=\"" + PropertiesReader.getSecurityProperties().getProperty("app.baseurl") + "/reset/passwordReset/" + token + "\">" + body + "</a>";
        EmailService.sendEmailWithContent(email, subject, contentHtml);
    }

    @RolesAllowed("unblockUser")
    @Override
    public Account unblockUser(String unblockedUserLogin, long version) throws BaseAppException {
        Account account = this.accountFacade.findByLogin(unblockedUserLogin);
        if (!(account.getVersion() == version)) {
            throw FacadeException.optimisticLock();
        }
        account.setActive(true);

        setUpdatedMetadata(account);
        return account;
    }

    @RolesAllowed("changeOtherClientData")
    @Override
    public Account changeOtherClientData(String login, String phoneNumber, Address addr, long version) throws BaseAppException {
        Account targetAccount = accountFacade.findByLogin(login);
        Client targetClient = (Client) getAccessLevel(targetAccount, AccessLevelType.CLIENT);
        if (!(targetClient.getVersion() == version)) {
            throw FacadeException.optimisticLock();
        }

        targetClient.setPhoneNumber(phoneNumber);

        Address targetAddress = targetClient.getHomeAddress();
        targetAddress.setHouseNumber(addr.getHouseNumber());
        targetAddress.setStreet(addr.getStreet());
        targetAddress.setPostalCode(addr.getPostalCode());
        targetAddress.setCity(addr.getCity());
        targetAddress.setCountry(addr.getCountry());

        setUpdatedMetadata(targetClient, targetAddress);
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

    @RolesAllowed("changeOtherAccountData")
    public Account changeOtherAccountData(Account updatedAccount) throws BaseAppException {
        Account targetAccount = accountFacade.findByLogin(updatedAccount.getLogin());

        if (!(targetAccount.getVersion() == (updatedAccount.getVersion()))) {
            throw FacadeException.optimisticLock();
        }
        targetAccount.setFirstName(updatedAccount.getFirstName());
        targetAccount.setSecondName(updatedAccount.getSecondName());
        targetAccount.setEmail(updatedAccount.getEmail());

        setUpdatedMetadata(targetAccount);
        return targetAccount;
    }

    @RolesAllowed("changeOtherBusinessWorkerData")
    @Override
    public Account changeOtherBusinessWorkerData(String login, String phoneNumber, long version) throws BaseAppException {
        Account targetAccount = accountFacade.findByLogin(login);
        BusinessWorker targetBusinessWorker = (BusinessWorker) getAccessLevel(targetAccount, AccessLevelType.BUSINESS_WORKER);
        if (!(targetBusinessWorker.getVersion() == version)) {
            throw FacadeException.optimisticLock();
        }
        targetBusinessWorker.setPhoneNumber(phoneNumber);
        setUpdatedMetadata(targetBusinessWorker);
        return targetAccount;
    }


    @RolesAllowed("changeEmail")
    @Override
    public void changeEmail(String login, long version, String newEmail) throws BaseAppException {
        Account account = accountFacade.findByLogin(login);
        if (!(account.getVersion() == version)) {
            throw FacadeException.optimisticLock();
        }
        account.setEmail(newEmail);

        setUpdatedMetadata(account);
    }

    private AccessLevel getAccessLevel(Account from, AccessLevelType target) throws AccountManagerException {
        Optional<AccessLevel> optionalAccessLevel = from.getAccessLevels().stream()
                .filter(accessLevel -> accessLevel.getAccessLevelType().equals(target)).findAny();

        return optionalAccessLevel.orElseThrow(() -> new AccountManagerException(ACCESS_LEVEL_DOES_NOT_EXIST_ERROR));
    }

    private void setAccountChanges(Account target, Account from) throws BaseAppException {
        if (!(target.getVersion() == from.getVersion())) {
            throw FacadeException.optimisticLock();
        }
        target.setFirstName(from.getFirstName());
        target.setSecondName(from.getSecondName());
    }


    @RolesAllowed("changeClientData")
    @Override
    public void changeClientData(Account fromAccount) throws BaseAppException {
        Account targetAccount = accountFacade.findByLogin(fromAccount.getLogin());

        if (!(fromAccount.getVersion() == targetAccount.getVersion())) {
            throw FacadeException.optimisticLock();
        }

        setAccountChanges(targetAccount, fromAccount);

        Client targetClient = (Client) getAccessLevel(targetAccount, AccessLevelType.CLIENT);

        Client fromClient = (Client) getAccessLevel(fromAccount, AccessLevelType.CLIENT);
        updateClient(fromClient, targetClient);

        Address targetAddress = targetClient.getHomeAddress();

        setUpdatedMetadata(targetClient, targetAddress, targetAccount);
    }

    @RolesAllowed("changeBusinessWorkerData")
    @Override
    public void changeBusinessWorkerData(Account fromAccount) throws BaseAppException {
        Account targetAccount = accountFacade.findByLogin(fromAccount.getLogin());

        if (!(fromAccount.getVersion() == targetAccount.getVersion())) {
            throw FacadeException.optimisticLock();
        }

        setAccountChanges(targetAccount, fromAccount);

        BusinessWorker targetBusinessWorker = (BusinessWorker) getAccessLevel(targetAccount, AccessLevelType.BUSINESS_WORKER);

        BusinessWorker fromBusinessWorker = (BusinessWorker) getAccessLevel(fromAccount, AccessLevelType.BUSINESS_WORKER);
        targetBusinessWorker.setPhoneNumber(fromBusinessWorker.getPhoneNumber());
        setUpdatedMetadata(targetBusinessWorker, targetAccount);
    }

    @PermitAll
    @Override
    public void updateIncorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time) throws BaseAppException {

        this.accountFacade.updateAuthenticateInfo(login, IpAddr, time, false);
        Account account = accountFacade.findByLogin(login);
        if (account.getNumberOfAuthenticationFailures() >= Long.parseLong(securityProperties.getProperty("max.incorrect.logins"))) {
            account.setActive(false);
            accountFacade.edit(account);
            Locale locale = new Locale(account.getLanguageType().getName().name());
            String body = i18n.getMessage(BLOCKED_ACCOUNT_BODY, locale);
            String subject = i18n.getMessage(BLOCKED_ACCOUNT_SUBJECT, locale);
            EmailService.sendEmailWithContent(account.getEmail().trim(), subject, body);
        }
    }

    @RolesAllowed("changeModeratorData")
    @Override
    public void changeModeratorData(Account fromAccount) throws BaseAppException {
        Account targetAccount = accountFacade.findByLogin(fromAccount.getLogin());

        if (!(fromAccount.getVersion() == targetAccount.getVersion())) {
            throw FacadeException.optimisticLock();
        }

        setAccountChanges(targetAccount, fromAccount);

        Moderator targetModerator = (Moderator) getAccessLevel(targetAccount, AccessLevelType.MODERATOR);
        setUpdatedMetadata(targetModerator, targetAccount);
    }

    @RolesAllowed("changeAdministratorData")
    @Override
    public void changeAdministratorData(Account fromAccount) throws BaseAppException {
        Account targetAccount = accountFacade.findByLogin(fromAccount.getLogin());

        if (!(fromAccount.getVersion() == targetAccount.getVersion())) {
            throw FacadeException.optimisticLock();
        }

        setAccountChanges(targetAccount, fromAccount);

        Administrator targetAdministrator = (Administrator) getAccessLevel(targetAccount, AccessLevelType.ADMINISTRATOR);

        setUpdatedMetadata(targetAdministrator, targetAccount);
    }


    @RolesAllowed({"getAccountByLogin", "getAccountDetailsByLogin", "selfGetAccountDetails", "getAccessLevelByLogin"})
    @Override
    public Account getAccountByLogin(String login) throws BaseAppException {
        return accountFacade.findByLogin(login);
    }

    @PermitAll
    @Override
    public String updateCorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time) throws BaseAppException {
        Account account = this.accountFacade.updateAuthenticateInfo(login, IpAddr, time, true);
        account.setNumberOfAuthenticationFailures(0);

        Map<String, Object> map = Map.of("accessLevels", account.getAccessLevels()
                .stream().map(accessLevel -> accessLevel.getAccessLevelType().name()).collect(Collectors.toList()));
        return JWTHandler.createToken(map, account.getLogin());
    }


    @RolesAllowed("authenticatedUser")
    @Override
    public void changeOwnPassword(String login, long version, String oldPassword, String newPassword) throws BaseAppException {
        Account account = accountFacade.findByLogin(login);

        if (!(account.getVersion() == version)) {
            throw FacadeException.optimisticLock();
        }

        if (!account.getPasswordHash().equals(DigestUtils.sha256Hex(oldPassword))) {
            throw new AccountManagerException(PASSWORDS_DONT_MATCH_ERROR);
        }

        account.setPasswordHash(DigestUtils.sha256Hex(newPassword));
        setUpdatedMetadata(account);
    }

    @RolesAllowed("authenticatedUser")
    public Account getCurrentUser() throws BaseAppException {
        return accountFacade.findByLogin(context.getUserPrincipal().getName());
    }

    private void setUpdatedMetadataWithModifier(Account modifier, BaseEntity... entities) throws BaseAppException {
        AlterTypeWrapper update = accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE);
        for (BaseEntity e : entities) {
            e.setAlterType(update);
            e.setAlteredBy(modifier);
        }
    }

    private void setUpdatedMetadata(BaseEntity... entities) throws BaseAppException {
        AlterTypeWrapper update = accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE);
        for (BaseEntity e : entities) {
            e.setAlterType(update);
            e.setAlteredBy(getCurrentUser());
        }
    }

    private void setCreatedMetadata(Account creator, BaseEntity... entities) throws BaseAppException {
        AlterTypeWrapper insert = accountFacade.getAlterTypeWrapperByAlterType(AlterType.INSERT);
        for (BaseEntity e : entities) {
            e.setAlterType(insert);
            e.setAlteredBy(creator);
            e.setCreatedBy(creator);
        }
    }

    @RolesAllowed("authenticatedUser")
    public void changeMode(String login, boolean newMode) throws BaseAppException {
        Account account = accountFacade.findByLogin(login);

        account.setDarkMode(newMode);
        setUpdatedMetadata(account);
    }

    @RolesAllowed("authenticatedUser")
    public String refreshJWTToken(String token) throws BaseAppException {
        Map<String, Claim> claims = JWTHandler.getClaimsFromToken(token);

        try {
            String login = claims.get("sub").asString();
            Set<String> accessLevelsFromToken = new HashSet<>(claims.get("accessLevels").asList(String.class));
            Account account = accountFacade.findByLogin(login);
            if (!canUserRefreshToken(account, accessLevelsFromToken)) {
                throw new AccountManagerException(TOKEN_REFRESH_ERROR);
            }
            return JWTHandler.refreshToken(token);
        } catch (NullPointerException e) {
            throw new AccountManagerException(TOKEN_REFRESH_ERROR);
        }
    }

    private boolean canUserRefreshToken(Account account, Set<String> tokenAccessLevels) {

        Set<String> accountValidAccessLevels = account.getAccessLevels().stream()
                .filter(accessLevel -> {
                            if (accessLevel instanceof BusinessWorker) {
                                BusinessWorker bw = (BusinessWorker) accessLevel;
                                return bw.isEnabled() && bw.isConfirmed();
                            }
                            return accessLevel.isEnabled();
                        }
                )
                .map(a -> a.getAccessLevelType().name()).collect(Collectors.toSet());

        boolean accessLevelsValid = tokenAccessLevels.equals(accountValidAccessLevels) && !tokenAccessLevels.isEmpty();

        return account.isActive() && account.isConfirmed() && accessLevelsValid;
    }
}
