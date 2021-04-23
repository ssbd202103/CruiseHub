package pl.lodz.p.it.ssbd2021.ssbd03.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * Klasa odpowiedzialna za zarządzanie tokenami JWT.
 */
public class JWTHandler {
    private static final Properties securityProperties = PropertiesReader.getSecurityProperties();

    /**
     * Metoda tworząca nowy token JWT
     *
     * @param claims  Zestaw par klucz-wartość w postaci Mapy reprezentujący stwierdzenia tokenu
     * @param subject String określający podmiot tokenu
     * @return Zwrócony token w postaci Stringa
     */
    public static String createToken(Map<String, Object> claims, String subject) {
        Algorithm algorithm = Algorithm.HMAC256(getJWTSecret());
        return JWT.create()
                .withPayload(claims).withSubject(subject)
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(Instant.now().plus(getDefaultValidityInMinutes(), ChronoUnit.MINUTES)))
                .sign(algorithm);
    }


    /**
     * Metoda walidująca istniejący token na podstawie użytego algorytmu oraz sekretu.
     * W przypadku niepowodzenia walidacji rzucany jest wyjątek JWTVerificationException
     *
     * @param token Przekazany token
     * @throws JWTVerificationException Wyjątek błędu walidacji
     */
    public static void validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(getJWTSecret());
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            verifier.verify(token);
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token validation failed");
        }
    }

    private static Long getDefaultValidityInMinutes() {
        try {
            return Long.parseLong(securityProperties.getProperty("jwt.validityInMinutes"));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("jwt.validityInMinutes value: {" + securityProperties.getProperty("jwt.validityInMinutes") + "} could not be parsed to Long, verify properties data");
        }
    }

    private static String getJWTSecret() {
        return securityProperties.getProperty("jwt.secret");
    }
}
