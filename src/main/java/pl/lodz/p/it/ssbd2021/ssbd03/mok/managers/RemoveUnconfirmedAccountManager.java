package pl.lodz.p.it.ssbd2021.ssbd03.mok.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.TokenWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AccountFacadeMok;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.TokenWrapperFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.security.JWTHandler;
import pl.lodz.p.it.ssbd2021.ssbd03.services.EmailService;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.VERIFICATION_EMAIL_BODY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.VERIFICATION_EMAIL_SUBJECT;


/**
 * Klasa odpowiadająca za cykliczne usuwanie niepotwierdzonych  kont
 */

@Singleton
@Startup
public class RemoveUnconfirmedAccountManager {

    @Inject
    private AccountFacadeMok accountFacade;
    @Inject
    private TokenWrapperFacade tokenWrapperFacade;
    @Inject
    private I18n ii18n;

    /**
     * Pobiera i usuwa niezatwierdzone konta, dla których minął 24-godzinny okres potwierdzenia
     */
    @Schedule(persistent = false)
    private void removeUnconfirmedAccounts() throws BaseAppException {

        List<Account> unconfirmed = accountFacade.getUnconfirmedAccounts();
        for (Account acc : unconfirmed
        ) {
            if (acc.getCreationDateTime().plus(1, ChronoUnit.DAYS).isBefore(LocalDateTime.now())) {
                String[] to = {acc.getEmail()};
                Locale locale = new Locale(acc.getLanguageType().getName().name());
                EmailService.sendFromGMail(to, ii18n.getMessage(I18n.REMOVE_UNCONFIRMED_ACCOUNT_SUBJECT, locale), ii18n.getMessage(I18n.REMOVE_UNCONFIRMED_ACCOUNT_BODY, locale));
                accountFacade.remove(acc);
            }
        }
    }

    @Schedule(hour = "*/12", minute = "*", second = "*", persistent = false)
    private void removeUsedTokens() throws BaseAppException {
        List<TokenWrapper> tokens = tokenWrapperFacade.getUsedToken();
        for (TokenWrapper tw :
                tokens) {
            tokenWrapperFacade.remove(tw);
        }
    }

    @Schedule(hour = "*/12", minute = "*", second = "*", persistent = false)
    private void removeUnusedTokens() throws BaseAppException {
        List<TokenWrapper> tokens = tokenWrapperFacade.getUnusedToken();
        for (TokenWrapper tw :
                tokens) {
            if (JWTHandler.getExpirationTimeFromToken(tw.getToken()).before(new Date(System.currentTimeMillis()))) {
                tokenWrapperFacade.remove(tw);
            }
        }
    }

    @Schedule(hour = "*/12", minute = "*", second = "*", persistent = false)
    private void sendActivationEmailWithToken() throws BaseAppException { // todo handle this exception
        List<Account> unconfirmed = accountFacade.getUnconfirmedAccounts();
        for (Account acc : unconfirmed
        ) {
            if (acc.getCreationDateTime().plus(12, ChronoUnit.HOURS).isBefore(LocalDateTime.now())) {
                Map<String, Object> claims = Map.of("version", acc.getVersion());
                String token = JWTHandler.createTokenEmail(claims, acc.getLogin());
                Locale locale = new Locale(acc.getLanguageType().getName().name());
                String subject = ii18n.getMessage(VERIFICATION_EMAIL_SUBJECT, locale);
                String body = ii18n.getMessage(VERIFICATION_EMAIL_BODY, locale);
                TokenWrapper tokenWrapper = TokenWrapper.builder().token(token).account(acc).used(false).build();
                this.tokenWrapperFacade.create(tokenWrapper);
                String contentHtml = "<a href=\"" + PropertiesReader.getSecurityProperties().getProperty("app.baseurl") + "/verify/accountVerification/" + token + "\">" + body + "</a>";
                EmailService.sendEmailWithContent(acc.getEmail().trim(), subject, contentHtml);
            }
        }
    }


}
