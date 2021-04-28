package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.NO_SUCH_ELEMENT_ERROR;


public class FacadeException extends BaseAppException {

    public FacadeException(String message) {
        super(message);
    }

    public FacadeException(String message, Throwable cause) {
        super(message, cause);
    }

    public static FacadeException noSuchElement() throws FacadeException {
        throw new FacadeException(NO_SUCH_ELEMENT_ERROR);
    }
}
