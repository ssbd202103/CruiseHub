package pl.lodz.p.it.ssbd2021.ssbd03.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.JWTException;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.interceptor.Interceptors;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

/**
 * Klasa odpowiedzialna za zarządzanie tokenami JWT.
 */
@Interceptors(TrackingInterceptor.class)
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
                .withJWTId(UUID.randomUUID().toString())
                .withIssuer(getJWTIssuer())
                .sign(algorithm);
    }

    public static String createTokenEmail(Map<String, ?> claims, String subject) {
        Algorithm algorithm = Algorithm.HMAC256(getJWTSecret());
        return JWT.create()
                .withPayload(claims).withSubject(subject)
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(Instant.now().plus(getDefaultValidityInHours(), ChronoUnit.HOURS)))
                .withJWTId(UUID.randomUUID().toString())
                .withIssuer(getJWTIssuer())
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
            throw new TokenExpiredException(ACCOUNT_VERIFICATION_TOKEN_EXPIRED_ERROR);
        }

        //getClaims returns Map<String, Claim> where Claim needs to be converted to Object type,
        //to satisfy allowed Claim types, thus mapping it explicitly
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
    public static Map<String, Claim> getClaimsFromToken(String token) throws JWTException {
        try {
            return JWT.decode(token).getClaims();
        } catch (JWTDecodeException e) {
            throw new JWTException(TOKEN_DECODE_ERROR);
        }
    }

    /**
     * Metoda zwrająca issuera;
     *
     * @param token Token poddawany dekodowaniu
     * @return issuer;
     * @throws JWTDecodeException Wyjątek błędu dekodowania
     */
    public static String getIssuerFromToken(String token) throws JWTException {
        try {
            return JWT.decode(token).getIssuer();
        } catch (JWTDecodeException e) {
            throw new JWTException(TOKEN_DECODE_ERROR);
        }
    }

    /**
     * Metoda zwrająca issuera;
     *
     * @param token Token poddawany dekodowaniu
     * @return issuer;
     * @throws JWTDecodeException Wyjątek błędu dekodowania
     */
    public static Date getExpirationTimeFromToken(String token) throws JWTException {
        try {
            return JWT.decode(token).getExpiresAt();
        } catch (JWTDecodeException e) {
            throw new JWTException(TOKEN_DECODE_ERROR);
        }
    }

    /**
     * Metoda walidująca istniejący token na podstawie użytego algorytmu oraz sekretu.
     * W przypadku niepowodzenia walidacji rzucany jest wyjątek JWTVerificationException
     *
     * @param token Przekazany token
     * @throws JWTVerificationException Wyjątek błędu walidacji
     */
    public static void validateToken(String token) throws JWTException {
        Algorithm algorithm = Algorithm.HMAC256(getJWTSecret());
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            verifier.verify(token);
        } catch (JWTVerificationException exception) {
            throw new JWTException(TOKEN_INVALIDATE_ERROR);
        }
    }

    private static long getDefaultValidityInMinutes() {
        try {
            return Long.parseLong(securityProperties.getProperty("jwt.validityInMinutes"));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("jwt.validityInMinutes value: {" + securityProperties.getProperty("jwt.validityInMinutes") + "} could not be parsed to long, verify properties data");
        }
    }

    private static long getDefaultValidityInHours() {
        try {
            return Long.parseLong(securityProperties.getProperty("jwt.validityInHours"));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("jwt.validityInHours value: {" + securityProperties.getProperty("jwt.validityInHours") + "} could not be parsed to long, verify properties data");
        }
    }

    private static String getJWTSecret() {
        return securityProperties.getProperty("jwt.secret");
    }

    private static String getJWTIssuer() {
        return securityProperties.getProperty("jwt.issuer");
    }
}
