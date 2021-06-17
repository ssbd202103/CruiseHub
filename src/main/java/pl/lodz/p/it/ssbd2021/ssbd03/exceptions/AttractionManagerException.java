package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

/**
 * Klasa reprezentująca wyjątki rzucane przez klasę AttractionManager
 */
public class AttractionManagerException extends BaseAppException {
    public AttractionManagerException(String message) {
        super(message);
    }

    public AttractionManagerException(String message, Throwable cause) {
        super(message, cause);
    }


}
