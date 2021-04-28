package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

/**
 * Klasa odpowiedzialna za wyjątek powstały w trakcie logowania
 */
public class AuthUnauthorized extends Exception {

    public AuthUnauthorized(String message) {
        super(message);
    }
}