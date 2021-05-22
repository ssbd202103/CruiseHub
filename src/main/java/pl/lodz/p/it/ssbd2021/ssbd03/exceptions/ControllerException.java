package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;

public class ControllerException extends BaseAppException {
    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerException(String message) {
        super(message);
    }

    public static ControllerException etagIdentityIntegrity() throws ControllerException {
        throw new ControllerException(I18n.ETAG_IDENTITY_INTEGRITY_ERROR);
    }
}
