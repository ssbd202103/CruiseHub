package pl.lodz.p.it.ssbd2021.ssbd03.common;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Klasa agregująca wartości Stringów poddawane internacjonalizacji, używane w backendzie aplikacji
 */
public class I18n {
    public static final String ETAG_IDENTITY_INTEGRITY_ERROR = "error.security.etag.integrity";
    public static final String OPTIMISTIC_LOCK_EXCEPTION = "error.integration.optimistic";
    public static final String OPERATION_NOT_AUTHORIZED_ERROR = "error.security.notAuthorized";
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
    public static final String ACCOUNT_NOT_VERIFIED_ERROR = "error.account.notVerified";
    public static final String ACCOUNT_NOT_ACTIVE_ERROR = "error.account.notActive";
    public static final String ACCOUNT_VERIFICATION_TOKEN_CONTENT_ERROR = "error.account.verification.contentError";
    public static final String TOKEN_EXPIRED_ERROR = "error.token.expired";
    public static final String TOKEN_REFRESH_ERROR = "error.token.refresh";
    public static final String ACCOUNT_VERIFICATION_TOKEN_ALREADY_VERIFIED_ERROR = "error.account.verification.alreadyVerifiedError";
    public static final String TOKEN_DECODE_ERROR = "error.token.decode";
    public static final String TOKEN_INVALIDATE_ERROR = "error.token.invalidate";
    public static final String TOKEN_ALREADY_USED_ERROR = "error.token.alreadyUsedError";
    public static final String REGEX_INVALID_POST_CODE = "error.regex.postCode";
    public static final String REGEX_INVALID_HOUSE_NUMBER = "error.regex.houseNumber";
    public static final String REGEX_INVALID_CITY = "error.regex.city";
    public static final String REGEX_INVALID_COUNTRY = "error.regex.country";
    public static final String REGEX_INVALID_STREET = "error.regex.street";
    public static final String REGEX_INVALID_PHONE_NUMBER = "error.regex.phoneNumber";
    public static final String REGEX_INVALID_LOGIN = "error.regex.login";
    public static final String REGEX_INVALID_PASSWORD = "error.regex.password";
    public static final String REGEX_INVALID_NAME = "error.regex.name";
    public static final String REGEX_INVALID_EMAIL = "error.regex.email";
    public static final String PHONE_NUMBER_SIZE = "error.size.phoneNumber";
    public static final String NAME_SIZE = "error.size.name";
    public static final String COUNTRY_SIZE = "error.size.country";
    public static final String CITY_SIZE = "error.size.city";
    public static final String STREET_SIZE = "error.size.street";
    public static final String COMPANY_SIZE = "error.size.companyName";
    public static final String POST_CODE_SIZE = "error.size.postCode";
    public static final String REGEX_INVALID_COMPANY_NAME = "error.regex.companyName";
    public static final String REQUESTED_PASSWORD_RESET_SUBJECT = "account.request.reset.password.subject";
    public static final String REQUESTED_PASSWORD_RESET_BODY = "account.request.reset.password.body";
    public static final String VERIFICATION_EMAIL_SUBJECT = "account.verification.subject";
    public static final String VERIFICATION_EMAIL_BODY = "account.verification.body";
    public static final String AUTH_CODE_EMAIL_SUBJECT = "auth.code.email.subject";
    public static final String AUTH_CODE_EMAIL_BODY = "auth.code.email.body";
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
    public static final String PASSWORDS_ARE_THE_SAME_ERROR = "error.password.change.newAndOldPasswordAreTheSameErorr";
    public static final String CODE_DONT_MATCH_ERROR = "error.code.dontMatchError";
    public static final String CODE_EXPIRE_ERROR = "error.code.expireError";
    public static final String CODE_ALREADY_USED_ERROR = "error.code.alreadyUsedError";
    public static final String CODE_IS_INCORRECT_ERROR = "error.code.incorrectCodeError";
    public static final String DATABASE_OPERATION_ERROR = "error.database.operation";
    public static final String LOGIN_RESERVED_ERROR = "error.database.loginReserved";
    public static final String EMAIL_RESERVED_ERROR = "error.database.emailReserved";
    public static final String USER_NOT_EXISTS_ERROR = "error.database.userNotExists";
    public static final String CONSTRAINT_VIOLATION_ERROR = "error.constraintViolation";
    public static final String CONSTRAINT_POSITIVE = "error.constraint.positive";
    public static final String CONSTRAINT_POSITIVE_OR_ZERO = "error.constraint.positiveOrZero";
    public static final String CONSTRAINT_NOT_EMPTY = "error.constraint.notEmpty";
    public static final String CONSTRAINT_NOT_NULL = "error.constraint.notNull";
    public static final String APP_FORBIDDEN = "error.forbidden";
    public static final String APP_INTERNAL_SERVER_ERROR = "error.internal.server";
    public static final String RATING_CONSTRAINT_ERROR = "error.constraint.rating";
    public static final String REQUEST_EMAIL_CHANGE_SUBJECT = "account.request.change.email.subject";
    public static final String REQUEST_EMAIL_CHANGE_BODY = "account.request.change.email.body";
    public static final String BUSINESS_WORKER_CONFIRMED = "error.business.worker.confirmed";
    public static final String LOG_IN_SUBJECT = "log.in.subject";
    public static final String LOG_IN_BODY = "log.in.body";
    public static final String ACCESS_LEVEL_PARSE_ERROR = "error.account.accessLevels.parse";
    public static final String ACCESS_LEVEL_GRANT_ADMINISTRATOR_SUBJECT = "account.accessLevels.grantAdministrator.subject";
    public static final String ACCESS_LEVEL_GRANT_ADMINISTRATOR_BODY = "account.accessLevels.grantAdministrator.body";
    public static final String ACCESS_LEVEL_GRANT_MODERATOR_SUBJECT = "account.accessLevels.grantModerator.subject";
    public static final String ACCESS_LEVEL_GRANT_MODERATOR_BODY = "account.accessLevels.grantModerator.body";
    public static final String ACCESS_LEVEL_DEACTIVATE_ADMINISTRATOR_SUBJECT = "account.accessLevels.deactivateAdministrator.subject";
    public static final String ACCESS_LEVEL_DEACTIVATE_ADMINISTRATOR_BODY = "account.accessLevels.deactivateAdministrator.body";
    public static final String ACCESS_LEVEL_REACTIVATE_ADMINISTRATOR_SUBJECT = "account.accessLevels.reactivateAdministrator.subject";
    public static final String ACCESS_LEVEL_REACTIVATE_ADMINISTRATOR_BODY = "account.accessLevels.reactivateAdministrator.body";
    public static final String ACCESS_LEVEL_DEACTIVATE_MODERATOR_SUBJECT = "account.accessLevels.deactivateModerator.subject";
    public static final String ACCESS_LEVEL_DEACTIVATE_MODERATOR_BODY = "account.accessLevels.deactivateModerator.body";
    public static final String ACCESS_LEVEL_REACTIVATE_MODERATOR_SUBJECT = "account.accessLevels.reactivateModerator.subject";
    public static final String ACCESS_LEVEL_REACTIVATE_MODERATOR_BODY = "account.accessLevels.reactivateModerator.body";
    public static final String ACCESS_LEVEL_DEACTIVATE_BUSINESS_WORKER_SUBJECT = "account.accessLevels.deactivateBusinessWorker.subject";
    public static final String ACCESS_LEVEL_DEACTIVATE_BUSINESS_WORKER_BODY = "account.accessLevels.deactivateBusinessWorker.body";
    public static final String ACCESS_LEVEL_REACTIVATE_BUSINESS_WORKER_SUBJECT = "account.accessLevels.reactivateBusinessWorker.subject";
    public static final String ACCESS_LEVEL_REACTIVATE_BUSINESS_WORKER_BODY = "account.accessLevels.reactivateBusinessWorker.body";
    public static final String ACCESS_LEVEL_DEACTIVATE_CLIENT_SUBJECT = "account.accessLevels.deactivateClient.subject";
    public static final String ACCESS_LEVEL_DEACTIVATE_CLIENT_BODY = "account.accessLevels.deactivateClient.body";
    public static final String ACCESS_LEVEL_REACTIVATE_CLIENT_SUBJECT = "account.accessLevels.reactivateClient.subject";
    public static final String ACCESS_LEVEL_REACTIVATE_CLIENT_BODY = "account.accessLevels.reactivateClient.body";
    public static final String CRUISE_GROUP_INACTIVE = "error.cruiseGroup.no.active";
    public static final String START_DATE_BEFORE_CURRENT_DATE = "error.startDate.before.currentDate";
    public static final String START_DATE_AFTER_END_DATE = "error.startDate.after.endDate";
    public static final String CRUISE_MAPPER_DATE_PARSE = "error.cruise.mapper.data.parse";
    public static final String CRUISE_MAPPER_UUID_PARSE = "error.cruise.mapper.uuid.parse";
    public static final String RESERVATION_MAPPER_UUID_PARSE = "error.reservation.mapper.uuid.parse";
    public static final String MAPPER_UUID_PARSE = "error.mapper.uuid.parse";
    public static final String MAPPER_LONG_PARSE = "error.mapper.long.parse";
    public static final String NO_SEATS_AVAILABLE = "reservation.noSeats";
    public static final String RATING_MAPPER_UUID_PARSE = "rating.mapper.uuid.parse";
    public static final String NOT_YOURS_CRUISE = "error.cruise.not.yours";
    public static final String CRUISE_GROUP_ALREADY_ACTIVE = "cruise.group.active.and.cannot.be.modified";
    public static final String CRUISE_GROUP_ALREADY_DEACTIVATED = "cruise.group.deactivated.and.cannot.be.deactivated";
    public static final String NON_REMOVABLE_ATTRACTION_CRUISE_ALREADY_PUBLISH = "error.attraction.non.removable";
    public static final String ATTRACTION_CREATION_CRUISE_PUBLISHED_ERROR = "error.attraction.create.cruisePublished";
    public static final String ATTRACTION_CREATION_CRUISE_DISABLED_ERROR = "error.attraction.create.cruiseDisabled";
    public static final String ATTRACTION_CREATION_CRUISE_ALREADY_STARTED_ERROR = "error.attraction.create.cruiseStartDate";
    public static final String ATTRACTION_EDIT_CRUISE_PUBLISHED_ERROR = "error.attraction.edit.cruisePublished";
    public static final String ATTRACTION_EDIT_CRUISE_DISABLED_ERROR = "error.attraction.edit.cruiseDisabled";
    public static final String ATTRACTION_EDIT_CRUISE_ALREADY_STARTED_ERROR = "error.attraction.edit.cruiseStartDate";
    public static final String ATTRACTION_DELETE_CRUISE_PUBLISHED_ERROR = "error.attraction.delete.cruisePublished";
    public static final String ATTRACTION_DELETE_CRUISE_DISABLED_ERROR = "error.attraction.delete.cruiseDisabled";
    public static final String ATTRACTION_DELETE_CRUISE_ALREADY_STARTED_ERROR = "error.attraction.delete.cruiseStartDate";
    public static final String BUSINESS_WORKER_ADD_CRUISE_TO_BAD_COMPANY = "businessWorker.add.cruise.to.bad.company";
    public static final String BUSINESS_WORKER_DONT_OWN_CRUISE_GROUP = "businessWorker.dont.own.cruise.group";
    public static final String CANNOT_FIND_ACCESS_LEVEL = "cannot.find.access.level";
    public static final String NOT_CORRECT_ACCESS_LEVEL = "not.correct.access.level";
    public static final String CRUISE_ALREADY_BLOCKED = "cruise.already.blocked";
    public static final String CANNOT_EDIT_THIS_CRUISE = "cruise.cannot.edit";
    public static final String CANNOT_BLOCK_THIS_CRUISE = "cruise.cannot.block";
    public static final String CANNOT_CANCEL_STARTED_CRUISE = "cruise.cannot.cancel.started";
    public static final String CANNOT_BOOK_STARTED_CRUISE = "cruise.cannot.book.started";
    public static final String RATING_EXISTS = "rating.exists";
    public static final String NO_SEATS_FOR_ATTRACTION = "no.seats.for.attraction";

    private ResourceBundle bundle;

    public String getMessage(String message, Locale locale) {
        bundle = ResourceBundle.getBundle("messages", locale);
        return bundle.getString(message);
    }
}
