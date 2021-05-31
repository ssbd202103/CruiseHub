package pl.lodz.p.it.ssbd2021.ssbd03.security;

import org.apache.commons.codec.digest.DigestUtils;

import javax.security.enterprise.identitystore.PasswordHash;
import java.util.Map;

/**
 * Klasa odpowiedzialna za hashowanie hasla
 */
public class SHA256Hash implements PasswordHash {

    @Override
    public void initialize(Map<String, String> parameters) {
    }

    /**
     * Metoda generujaca hash hasla na podstawie jego postaci jawnej
     * @param password Haslo w postaci jawnej
     * @return Hash hasla
     */
    @Override
    public String generate(char[] password) {
        return DigestUtils.sha256Hex(new String(password));
    }

    /**
     * Metoda weryfikujaca wygenerowany hash
     * @param password Haslo
     * @param hashedPassword Hash hasla
     * @return Wartosc true, jezeli weryfikacja przebiegla pomyslnie. W przyciwnym wypadku false
     */
    @Override
    public boolean verify(char[] password, String hashedPassword) {
        return hashedPassword.equals(generate(password));
    }
}
