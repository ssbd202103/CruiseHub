package pl.lodz.p.it.ssbd2021.ssbd03.common;

import javax.xml.transform.sax.SAXResult;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Klasa agregująca wartości Stringów poddawane internacjonalizacji, używane w backendzie aplikacji
 */
public class I18n {
    public static final String ETAG_IDENTITY_INTEGRITY_ERROR = "error.security.etag.integrity";
    public static final String OPTIMISTIC_EXCEPTION = "exception.optimistic";
    public static final String NO_SUCH_ELEMENT_ERROR = "error.facade.noSuchElement";
    public static final String ACCESS_LEVEL_ALREADY_ASSIGNED_ERROR = "error.account.accessLevels.alreadyAssigned";
    public static final String ACCESS_LEVEL_NOT_ASSIGNED_ERROR = "error.account.accessLevels.notAssigned";
    public static final String ACCESS_LEVEL_NOT_ASSIGNABLE_ERROR = "error.account.accessLevels.notAssignable";
    public static final String ACCESS_LEVEL_ALREADY_ENABLED_ERROR = "error.account.accessLevels.alreadyDisabled";
    public static final String ACCESS_LEVEL_ALREADY_DISABLED_ERROR = "error.account.accessLevels.alreadyEnabled";
    public static final String ETAG_EMPTY_ERROR = "error.security.etag.empty";
    public static final String ETAG_INVALID_ERROR = "error.security.etag.invalid";
    public static final String SERIALIZATION_PARSING_ERROR = "error.parsing.serialization";
    public static final String ACCESS_LEVEL_DOES_NOT_EXIST_ERROR = "error.account.accessLevels.doesNotExist";
    public static final String PASSWORD_RESET_IDENTITY_ERROR = "error.password.reset.wrongIdentity";
    public static final String EMAIL_SERVICE_INCORRECT_EMAIL = "error.email.incorrect";
    public static final String EMAIL_SERVICE_INACCESSIBLE = "error.emailService.inaccessible";
    public static final String PASSWORD_RESET_TOKEN_CONTENT_ERROR = "error.password.reset.contentError";
    public static final String ACCOUNT_VERIFICATION_TOKEN_CONTENT_ERROR = "error.account.verification.contentError";
    public static final String ACCOUNT_VERIFICATION_TOKEN_EXPIRE_ERROR = "error.account.verification.expireError";
    public static final String ACCOUNT_VERIFICATION_TOKEN_ALREADY_VERIFIED_ERROR = "error.account.verification.alreadyVerifiedError";
    public static final String REQUESTED_PASSWORD_RESET_SUBJECT = "account.request.reset.password.subject";
    public static final String REQUESTED_PASSWORD_RESET_BODY = "account.request.reset.password.body";
    public static final String VERIFICATION_EMAIL_SUBJECT = "account.verification.subject";
    public static final String VERIFICATION_EMAIL_BODY = "account.verification.body";
    public static final String REMOVE_UNCONFIRMED_ACCOUNT_SUBJECT ="account.remove.unconfirmed.subject";
    public static final String REMOVE_UNCONFIRMED_ACCOUNT_BODY ="account.remove.unconfirmed.body";
    public static final String BLOCKED_ACCOUNT_SUBJECT = "account.block.subject";
    public static final String UNBLOCKED_ACCOUNT_SUBJECT = "account.unblock.subject";
    public static final String ACTIVATE_ACCOUNT_SUBJECT = "account.activate.subject";
    public static final String BLOCKED_ACCOUNT_BODY = "account.block.body";
    public static final String UNBLOCKED_ACCOUNT_BODY = "account.unblock.body";
    public static final String ACTIVATE_ACCOUNT_BODY = "account.activate.body";
    public static final String INCORRECT_LOGIN = "auth.incorrect.login";
    public static final String INCORRECT_PASSWORD = "auth.incorrect.password";

    private ResourceBundle bundle;
        public String getMessage(String message, Locale locale) {
            bundle= ResourceBundle.getBundle("messages",locale);
            return bundle.getString(message);
        }
}
