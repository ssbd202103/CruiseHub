package pl.lodz.p.it.ssbd2021.ssbd03.security;

/**
 * Interfejs udostępniający metody zwracające wartość ETagu
 */
public interface SignableEntity {
    String getSignablePayload();
}
