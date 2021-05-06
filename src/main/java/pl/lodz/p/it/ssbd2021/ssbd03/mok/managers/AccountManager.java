package pl.lodz.p.it.ssbd2021.ssbd03.mok.managers;

import com.auth0.jwt.interfaces.Claim;
import lombok.Data;
import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Administrator;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Moderator;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.AccountManagerException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.JWTException;
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
import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;

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
    I18n ii18n;

    @Override
    public Account getAccountByLogin(String login) throws BaseAppException {
        return accountFacade.findByLogin(login);
    }

    @Override
    public void createClientAccount(Account account, Client client) throws BaseAppException {
        AlterTypeWrapper insertAlterType = accountFacade.getAlterTypeWrapperByAlterType(AlterType.INSERT);

        setAccessLevelInitialMetadata(account, client, true);

        client.getHomeAddress().setCreatedBy(account);
        client.getHomeAddress().setAlteredBy(account);
        client.getHomeAddress().setAlterType(insertAlterType);

        accountFacade.create(account);
        if(accountFacade.findByLogin(account.getLogin()) != null) {
            sendVerificationEmail(account);
        }

    }

    @Override
    public void createBusinessWorkerAccount(Account account, BusinessWorker businessWorker, String companyName) throws BaseAppException {

        setAccessLevelInitialMetadata(account, businessWorker, true);
        businessWorker.setCompany(companyFacadeMok.getCompanyByName(companyName));

        this.accountFacade.create(account);
        if(accountFacade.findByLogin(account.getLogin()) != null) {
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
        Account account;
        try {
            account = accountFacade.findByLogin(accountLogin);
            account.setVersion(accountVersion); //to assure optimistic lock mechanism
            if (account.getAccessLevels().stream().anyMatch(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.ADMINISTRATOR)) {
                throw new AccountManagerException(ACCESS_LEVEL_ALREADY_ASSIGNED_ERROR);
            }
        } catch (FacadeException e) {
            throw new AccountManagerException(e.getMessage());
        }

        Administrator administrator = new Administrator(true);
        setAccessLevelInitialMetadata(account, administrator, false);

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
        String token = JWTHandler.createTokenEmail(claims,account.getLogin());
        Locale locale = new Locale(account.getLanguageType().getName().name());
        String subject = ii18n.getMessage(VERIFICATION_EMAIL_SUBJECT, locale);
        String body = ii18n.getMessage(VERIFICATION_EMAIL_BODY, locale);
        String contentHtml = "<a href=\"http://" + PropertiesReader.getSecurityProperties().getProperty("app.baseurl") + "/verify/accountVerification/" + token + "\">" + body + "</a>";
        EmailService.sendEmailWithContent(account.getEmail().trim(), subject, contentHtml);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountFacade.findAll();
    }

    @Override
    public void blockUser(long id) {
        this.accountFacade.blockUser(id);
    }

    @Override
    public void requestPasswordReset(String login) throws BaseAppException {
        Account account = this.accountFacade.findByLogin(login);
        Map<String, Object> claims = Map.of("version", account.getVersion());
        String token = JWTHandler.createToken(claims, login);
        String contentHtml = "<a href=\"http://" + PropertiesReader.getSecurityProperties().getProperty("app.baseurl") + "/reset/passwordReset/" + token + "\">Reset password</a>";
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
                account.setConfirmed(true);
                account.setVersion(claims.get("version").asLong());
                account.setAlterType(accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE));
                account.setAlteredBy(account);
            } else {
                throw new AccountManagerException(PASSWORD_VERIFICATION_TOKEN_EXPIRE_ERROR);
            }
        }
        else {
            throw new AccountManagerException(PASSWORD_VERIFICATION_TOKEN_CONTENT_ERROR);
        }
    }
}
