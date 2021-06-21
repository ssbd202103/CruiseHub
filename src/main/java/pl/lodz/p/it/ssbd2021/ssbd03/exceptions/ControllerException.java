package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;

/**
 * Klasa reprezentująca wyjątki które występują w kontrolerze
 */
public class ControllerException extends BaseAppException {
    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerException(String message) {
        super(message);
    }

}
