package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

/**
 * Klasa reprezentująca wyjątki rzucane przez klasę CruiseManager
 */
public class ReservationManagerException extends BaseAppException {
    public ReservationManagerException(String message) {
        super(message);
    }

    public ReservationManagerException(String message, Throwable cause) {
        super(message, cause);
    }


}
