package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

/**
 * Klasa reprezentująca wyjątek rzucany przez klasę AccountManagera
 */
public class AccountManagerException extends BaseAppException {
    public AccountManagerException(String message) {
        super(message);
    }

    public AccountManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
