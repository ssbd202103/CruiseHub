package pl.lodz.p.it.ssbd2021.ssbd03.common;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Klasa agregująca wartości Stringów poddawane internacjonalizacji, używane w backendzie aplikacji
 */
public class I18n {
    public static final String ETAG_IDENTITY_INTEGRITY_ERROR = "error.security.etag.integrity";
    public static final String OPTIMISTIC_LOCK_EXCEPTION = "exception.optimistic";
    public static final String NO_SUCH_ELEMENT_ERROR = "error.facade.noSuchElement";
    public static final String ACCESS_LEVEL_ALREADY_ASSIGNED_ERROR = "error.account.accessLevels.alreadyAssigned";
    public static final String ACCESS_LEVEL_NOT_ASSIGNED_ERROR = "error.account.accessLevels.notAssigned";
    public static final String ACCESS_LEVEL_NOT_ASSIGNABLE_ERROR = "error.account.accessLevels.notAssignable";
    public static final String ACCESS_LEVEL_ALREADY_ENABLED_ERROR = "error.account.accessLevels.alreadyDisabled";
    public static final String ACCESS_LEVEL_ALREADY_DISABLED_ERROR = "error.account.accessLevels.alreadyEnabled";
    public static final String ETAG_EMPTY_ERROR = "error.security.etag.empty";
    public static final String ETAG_CREATION_ERROR = "error.security.etag.creation";
    public static final String ETAG_INVALID_ERROR = "error.security.etag.invalid";
    public static final String SERIALIZATION_PARSING_ERROR = "error.parsing.serialization";
    public static final String ACCESS_LEVEL_DOES_NOT_EXIST_ERROR = "error.account.accessLevels.doesNotExist";
    public static final String PASSWORD_RESET_IDENTITY_ERROR = "error.password.reset.wrongIdentity";
    public static final String EMAIL_SERVICE_INCORRECT_EMAIL = "error.email.incorrect";
    public static final String EMAIL_SERVICE_INACCESSIBLE = "error.emailService.inaccessible";
    public static final String PASSWORD_RESET_TOKEN_CONTENT_ERROR = "error.password.reset.contentError";
    public static final String PASSWORD_RESET_USED_TOKEN_ERROR = "error.password.reset.tokenUsed";
    public static final String PASSWORD_RESET_ACCOUNT_NOT_VERIFIED_ERROR = "error.password.reset.account.notVerifiedError";
    public static final String ACCOUNT_VERIFICATION_TOKEN_CONTENT_ERROR = "error.account.verification.contentError";
    public static final String TOKEN_EXPIRED_ERROR = "error.token.expired";
    public static final String TOKEN_REFRESH_ERROR = "error.token.refresh";
    public static final String ACCOUNT_VERIFICATION_TOKEN_ALREADY_VERIFIED_ERROR = "error.account.verification.alreadyVerifiedError";
    public static final String TOKEN_DECODE_ERROR = "error.token.decode";
    public static final String TOKEN_INVALIDATE_ERROR = "error.token.invalidate";
    public static final String REGEX_INVALID_POST_CODE = "error.regex.postCode";
    public static final String REGEX_INVALID_CITY = "error.regex.city";
    public static final String REGEX_INVALID_COUNTRY = "error.regex.country";
    public static final String REGEX_INVALID_STREET = "error.regex.street";
    public static final String REGEX_INVALID_PHONE_NUMBER = "error.regex.phoneNumber";
    public static final String REGEX_INVALID_LOGIN = "error.regex.login";
    public static final String REGEX_INVALID_PASSWORD = "error.regex.password";
    public static final String REGEX_INVALID_NAME = "error.regex.name";
    public static final String REGEX_INVALID_EMAIL = "error.regex.email";
    public static final String REGEX_INVALID_COMPANY_NAME = "error.regex.companyName";
    public static final String REQUESTED_PASSWORD_RESET_SUBJECT = "account.request.reset.password.subject";
    public static final String REQUESTED_PASSWORD_RESET_BODY = "account.request.reset.password.body";
    public static final String VERIFICATION_EMAIL_SUBJECT = "account.verification.subject";
    public static final String VERIFICATION_EMAIL_BODY = "account.verification.body";
    public static final String REMOVE_UNCONFIRMED_ACCOUNT_SUBJECT = "account.remove.unconfirmed.subject";
    public static final String REMOVE_UNCONFIRMED_ACCOUNT_BODY = "account.remove.unconfirmed.body";
    public static final String BLOCKED_ACCOUNT_SUBJECT = "account.block.subject";
    public static final String UNBLOCKED_ACCOUNT_SUBJECT = "account.unblock.subject";
    public static final String ACTIVATE_ACCOUNT_SUBJECT = "account.activate.subject";
    public static final String BLOCKED_ACCOUNT_BODY = "account.block.body";
    public static final String UNBLOCKED_ACCOUNT_BODY = "account.unblock.body";
    public static final String ACTIVATE_ACCOUNT_BODY = "account.activate.body";
    public static final String INCORRECT_LOGIN = "auth.incorrect.login";
    public static final String INCORRECT_PASSWORD = "auth.incorrect.password";
    public static final String PASSWORDS_DONT_MATCH_ERROR = "error.password.change.oldPasswordError";
    public static final String DATABASE_OPERATION_ERROR = "error.database.operation";
    public static final String LOGIN_RESERVED_ERROR = "error.database.loginReserved";
    public static final String EMAIL_RESERVED_ERROR = "error.database.emailReserved";
    public static final String CONSTRAINT_VIOLATION_ERROR = "error.constraintViolation";
    public static final String CONSTRAINT_POSITIVE_ERROR = "error.constraint.positive";
    public static final String CONSTRAINT_POSITIVE_OR_ZERO_ERROR = "error.constraint.positiveOrZero";
    public static final String CONSTRAINT_NOT_EMPTY = "error.constraint.notEmpty";
    public static final String CONSTRAINT_NOT_NULL = "error.constraint.notNull";
    public static final String APP_FORBIDDEN = "error.forbidden";
    public static final String APP_INTERNAL_SERVER_ERROR = "error.internal.server";
    public static final String RATING_CONSTRAINT_ERROR = "error.constraint.rating";
    public static final String REQUEST_EMAIL_CHANGE_SUBJECT = "account.request.change.email.subject";
    public static final String REQUEST_EMAIL_CHANGE_BODY = "account.request.change.email.body";

    private ResourceBundle bundle;

    public String getMessage(String message, Locale locale) {
        bundle = ResourceBundle.getBundle("messages", locale);
        return bundle.getString(message);
    }
}
