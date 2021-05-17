package pl.lodz.p.it.ssbd2021.ssbd03.mok.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.TokenWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.EmailServiceException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.TokenWrapperFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.services.EmailService;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;

import javax.ejb.*;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;


/**
 * Klasa odpowiadająca za cykliczne usuwanie niepotwierdzonych  kont
 */

@Singleton
@Startup
public class RemoveUnconfirmedAccountManager {

    @Inject
    private AccountFacade accountFacade;
    @Inject
    private TokenWrapperFacade tokenWrapperFacade;
    @Inject
    private I18n ii18n;

    /**
     * Pobiera i usuwa niezatwierdzone konta, dla których minął 24-godzinny okres potwierdzenia
     */
    @Schedule(persistent = false)
    private void removeUnconfirmedAccounts() {

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
    private void removeUsedTokens() {
        List<TokenWrapper> tokens = tokenWrapperFacade.getUsedToken();
        for (TokenWrapper tw :
                tokens) {
            tokenWrapperFacade.remove(tw);
        }
    }

    @Schedule(hour = "*/12", minute = "*", second = "*", persistent = false)
    private void sendActivationEmailWithToken() throws EmailServiceException { // todo handle this exception
        List<TokenWrapper> tokens = tokenWrapperFacade.getUnUsedToken();
        for (TokenWrapper tw :
                tokens) {
            String contentHtml = "<a href=\"" + PropertiesReader.getSecurityProperties().getProperty("app.baseurl") + "/reset/passwordReset/" + tw.getToken() + "\">Reset password</a>";
            EmailService.sendEmailWithContent(tw.getAccount().getEmail().trim(), "hello", contentHtml);
        }
    }


}
