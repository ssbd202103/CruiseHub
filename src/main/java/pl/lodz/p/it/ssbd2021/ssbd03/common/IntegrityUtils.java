package pl.lodz.p.it.ssbd2021.ssbd03.common;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.ETagException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;

public class IntegrityUtils {
    private IntegrityUtils() {
    }

    public static void checkForOptimisticLock(BaseEntity entity, long version) throws BaseAppException {
        if (entity.getVersion() != version) {
            throw FacadeException.optimisticLock();
        }
    }

    public static void checkEtagIntegrity(SignableEntity entity, String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(entity, etag)) {
            throw ETagException.etagIdentityIntegrity();
        }
    }
}
