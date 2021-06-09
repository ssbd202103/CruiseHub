package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

/**
 * Klasa reprezentująca wyjątek rzucany przez klasę CompanyManager.
 */
public class CompanyMangerException extends BaseAppException {
    public CompanyMangerException(String message) {
        super(message);
    }

    public CompanyMangerException(String message, Throwable cause) {
        super(message, cause);
    }
}
