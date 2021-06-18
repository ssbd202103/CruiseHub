package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

/**
 * Klasa reprezentująca wyjątki rzucane przez klasę CruiseManager
 */
public class CruiseManagerException extends BaseAppException {
    public CruiseManagerException(String message) {
        super(message);
    }

    public CruiseManagerException(String message, Throwable cause) {
        super(message, cause);
    }


}
