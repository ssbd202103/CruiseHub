package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

import javax.ejb.ApplicationException;

/**
 * Klasa abstrakcyjna reprezentująca podstawowy wyjątek aplikacji.
 * Po jego wystąpieniu transakcje bazodanowe są odwoływane
 */
@ApplicationException(rollback = true)
abstract public class BaseAppException extends Exception {

    protected BaseAppException(String message, Throwable cause) {
        super(message, cause);
    }

    protected BaseAppException(String message) {
        super(message);
    }

}
