package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

/**
 * Klasa odpowiedzialna za wyjątek powstały w trakcie logowania
 */
public class AccountBlockedOrNotConfirmed extends Exception {

    public AccountBlockedOrNotConfirmed(String message) {
        super(message);
    }

    public AccountBlockedOrNotConfirmed() {
    }
}