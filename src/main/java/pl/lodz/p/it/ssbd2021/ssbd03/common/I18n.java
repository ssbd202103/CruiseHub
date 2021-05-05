package pl.lodz.p.it.ssbd2021.ssbd03.common;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Klasa agregująca wartości Stringów poddawane internacjonalizacji, używane w backendzie aplikacji
 */
public class I18n {
    public static final String NO_SUCH_ELEMENT_ERROR = "error.facade.noSuchElement";
    public static final String ACCESS_LEVEL_ALREADY_ASSIGNED_ERROR = "error.account.accessLevels.alreadyAssigned";
    public static final String ACCESS_LEVEL_NOT_ASSIGNABLE_ERROR = "error.account.accessLevels.notAssignable";
    public static final String ACCESS_LEVEL_DOES_NOT_EXIST_ERROR = "error.account.accessLevels.doesNotExist";
    public static final String ETAG_EMPTY_ERROR = "error.security.etag.empty";
    public static final String ETAG_INVALID_ERROR = "error.security.etag.invalid";
    public static final String PASSWORD_RESET_IDENTITY_ERROR = "error.password.reset.wrongIdentity";
    public static final String EMAIL_SERVICE_INCORRECT_EMAIL = "error.email.incorrect";
    public static final String EMAIL_SERVICE_INACCESSIBLE = "error.emailService.inaccessible";
    public static final String PASSWORD_RESET_TOKEN_CONTENT_ERROR = "error.password.reset.contentError";
    public static final String REQUESTED_PASSWORD_RESET_SUBJECT = "account.request.reset.password.subject";
    public static final String REQUESTED_PASSWORD_RESET_BODY = "account.request.reset.password.body";

        private ResourceBundle bundle;
        public String getMessage(String message, Locale locale) {
            bundle= ResourceBundle.getBundle("messages",locale);
            return bundle.getString(message);
        }
}
