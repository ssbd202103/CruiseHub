package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.ETAG_IDENTITY_INTEGRITY_ERROR;

/**
 * Klasa reprezentująca wyjątki związane z ETagiem
 */
public class ETagException extends BaseAppException {
    public ETagException(String message, Throwable cause) {
        super(message, cause);
    }

    public ETagException(String message) {
        super(message);
    }

    public static ETagException etagIdentityIntegrity() throws ETagException {
        throw new ETagException(ETAG_IDENTITY_INTEGRITY_ERROR);
    }
}
