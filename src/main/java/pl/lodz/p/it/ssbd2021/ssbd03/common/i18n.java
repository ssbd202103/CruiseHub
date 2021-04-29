package pl.lodz.p.it.ssbd2021.ssbd03.common;

import java.util.Locale;
import java.util.ResourceBundle;

public class i18n {
    public static final String REMOVE_UNCONFIRMED_ACCOUNT_SUBJECT ="account.remove.unconfirmed.subject";
    public static final String REMOVE_UNCONFIRMED_ACCOUNT_BODY ="account.remove.unconfirmed.body";
    private ResourceBundle bundle;
    public String getMessage(String message, Locale locale) {
        bundle= ResourceBundle.getBundle("messages",locale);
        return bundle.getString(message);
    }
}
