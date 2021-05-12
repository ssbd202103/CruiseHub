package pl.lodz.p.it.ssbd2021.ssbd03.security;

import org.apache.commons.codec.digest.DigestUtils;

import javax.security.enterprise.identitystore.PasswordHash;
import java.util.Map;

public class SHA256Hash implements PasswordHash {

    @Override
    public void initialize(Map<String, String> parameters) {

    }

    @Override
    public String generate(char[] password) {
        return DigestUtils.sha256Hex(new String(password));
    }

    @Override
    public boolean verify(char[] password, String hashedPassword) {
        return hashedPassword.equals(generate(password));
    }
}
