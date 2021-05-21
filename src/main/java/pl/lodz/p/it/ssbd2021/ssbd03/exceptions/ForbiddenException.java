package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.APP_FORBIDDEN;

public class ForbiddenException extends Exception { // zeby nie doszlo do naduzyc bedziemy recznie rzucac
    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenException forbidden() {
        return new ForbiddenException(APP_FORBIDDEN);
    }
}
