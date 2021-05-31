package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;

/**
 * Klasa reprezentująca wyjątek z tokenem JWT
 */
public class JWTException extends BaseAppException {

    public JWTException(String message, Throwable cause) {
        super(message, cause);
    }

    public JWTException(String message) {
        super(message);
    }

    public static JWTException tokenExpired() throws JWTException {
        throw new JWTException(I18n.TOKEN_EXPIRED_ERROR);
    }
}
