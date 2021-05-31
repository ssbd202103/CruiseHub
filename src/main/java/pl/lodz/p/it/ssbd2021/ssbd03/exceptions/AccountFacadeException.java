package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.EMAIL_RESERVED_ERROR;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.LOGIN_RESERVED_ERROR;

/**
 * Klasa reprezentujący wyjątki zaistniałe w fasadzie kont
 */
public class AccountFacadeException extends FacadeException {
    public AccountFacadeException(String message) {
        super(message);
    }

    public AccountFacadeException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AccountFacadeException loginReserved(Throwable cause) throws AccountFacadeException {
        throw new AccountFacadeException(LOGIN_RESERVED_ERROR, cause);
    }

    public static AccountFacadeException emailReserved(Throwable cause) throws AccountFacadeException {
        throw new AccountFacadeException(EMAIL_RESERVED_ERROR, cause);
    }
}
