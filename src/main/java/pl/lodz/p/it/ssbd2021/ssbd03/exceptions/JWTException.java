package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

public class JWTException extends BaseAppException {

    public JWTException(String message, Throwable cause) {
        super(message, cause);
    }

    public JWTException(String message) {
        super(message);
    }
}
