package pl.lodz.p.it.ssbd2021.ssbd03.mok.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.services.EmailService;
import pl.lodz.p.it.ssbd2021.ssbd03.common.i18n;
import javax.ejb.*;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;


/**
 * Klasa odpowiadająca za cykliczne usuwanie nieaktywnych kont
 */

@Singleton
@Startup
public class RemoveUnconfirmedAccountManager {

    @Inject
    AccountFacade accountFacade;
    @Inject
    i18n ii18n;

    /**
     * Pobiera i usuwa niezatwierdzone konta, dla których minął 24-godzinny okres potwierdzenia  
     */
    @Schedule(persistent = false)
    private void removeUnconfirmedAccounts() {

            List<Account> unconfirmed = accountFacade.getUnconfirmedAccounts();
            for (Account acc : unconfirmed
            ) {
                if (acc.getCreationDateTime().plus(1, ChronoUnit.MINUTES).isBefore(LocalDateTime.now())) {
                    String[] to = {acc.getEmail()};
                    Locale locale=new Locale( acc.getLanguageType().getName().name());
                    EmailService.sendFromGMail(to,ii18n.getMessage(i18n.REMOVE_UNCONFIRMED_ACCOUNT_SUBJECT,locale), ii18n.getMessage(i18n.REMOVE_UNCONFIRMED_ACCOUNT_BODY,locale));
                    accountFacade.remove(acc);
                }
            }


    }


}
