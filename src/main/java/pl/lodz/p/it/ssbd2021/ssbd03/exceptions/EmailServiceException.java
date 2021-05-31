package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

/**
 * Klasa reprezentująca wyjątki związane z serwisem email
 */
public class EmailServiceException extends BaseAppException {
    protected EmailServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailServiceException(String message) {
        super(message);
    }
}
