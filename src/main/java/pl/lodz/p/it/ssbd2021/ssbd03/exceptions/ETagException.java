package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

public class ETagException extends BaseAppException {
    public ETagException(String message, Throwable cause) {
        super(message, cause);
    }

    public ETagException(String message) {
        super(message);
    }
}
