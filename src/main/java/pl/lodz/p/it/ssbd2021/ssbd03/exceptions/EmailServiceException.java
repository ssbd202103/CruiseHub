package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

public class EmailServiceException extends BaseAppException {
    protected EmailServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailServiceException(String message) {
        super(message);
    }
}
