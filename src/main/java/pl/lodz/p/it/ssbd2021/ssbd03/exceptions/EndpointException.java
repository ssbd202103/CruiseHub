package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

/**
 *  * Klasa reprezentująca wyjątek rzucany przez klasę endpointa
 */
public class EndpointException extends BaseAppException {
    public EndpointException(String message, Throwable cause) {
        super(message, cause);
    }

    public EndpointException(String message) {
        super(message);
    }
}
