package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;


/**
 *  Klasa reprezentująca wyjątek rzucany przez klasę fasady
 */
public class FacadeException extends BaseAppException {

    public FacadeException(String message) {
        super(message);
    }

    public FacadeException(String message, Throwable cause) {
        super(message, cause);
    }

    public static FacadeException noSuchElement() throws BaseAppException {
        throw new FacadeException(NO_SUCH_ELEMENT_ERROR);
    }
    public static FacadeException optimisticLock() throws BaseAppException {
        throw new FacadeException(OPTIMISTIC_LOCK_EXCEPTION);
    }
}
