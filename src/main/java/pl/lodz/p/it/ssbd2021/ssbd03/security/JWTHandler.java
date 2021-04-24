package pl.lodz.p.it.ssbd2021.ssbd03.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

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
    public static String createToken(Map<String, ?> claims, String subject) {
        Algorithm algorithm = Algorithm.HMAC256(getJWTSecret());
        return JWT.create()
                .withPayload(claims).withSubject(subject)
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(Instant.now().plus(getDefaultValidityInMinutes(), ChronoUnit.MINUTES)))
                .sign(algorithm);
    }

    /**
     * Metoda pozwalająca na stworzenie odświeżonego tokenu poprzez podanie już istniejącego.
     * W przypadku próby odświeżenia wygaśniętego tokenu rzucany jest wyjątek TokenExpiredException
     *
     * @param token Istniejący token
     * @return Nowy token JWT z zachowaniem oryginalnego payloadu
     * @throws TokenExpiredException Wyjątek wygaśniętego tokenu
     */
    public static String refreshToken(String token) {
        DecodedJWT decodedToken = JWT.decode(token);
        if (decodedToken.getExpiresAt().before(new Date())) {
            throw new TokenExpiredException("Cannot refresh expired token");
        }

        //getClaims returns Map<String, Claim> where Claim value needs to be explicitly converted to Object type,
        // to fit Map<String, Object>, thus mapping
        Map<String, Object> convertedClaims = decodedToken.getClaims().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().as(Object.class)));
        return createToken(convertedClaims, decodedToken.getSubject());
    }


    /**
     * Metoda zwrająca zestaw par klucz-wartość stwierdzeń tokenu w postaci Mapy &lt;String, Claim&gt;
     *
     * @param token Token poddawany dekodowaniu
     * @return Mapa stwierdzeń &lt;String, Claim&gt;
     * @throws JWTDecodeException Wyjątek błędu dekodowania
     */
    public static Map<String, Claim> getClaimsFromToken(String token) {
        try {
            return JWT.decode(token).getClaims();
        } catch (JWTDecodeException e) {
            throw new JWTDecodeException("Provided token could not be decoded");
        }
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
