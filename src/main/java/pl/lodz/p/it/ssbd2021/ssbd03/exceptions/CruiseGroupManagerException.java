package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

/**
 * Klasa reprezentująca wyjątek rzucany przez klasę CruiseGroupManager
 */
public class CruiseGroupManagerException extends BaseAppException{
    public CruiseGroupManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CruiseGroupManagerException(String message) {
        super(message);
    }
}
