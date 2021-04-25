package pl.lodz.p.it.ssbd2021.ssbd03.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import java.text.ParseException;

/**
 * Klasa udostępniająca statyczne metody obsługujące ETag
 */
public class EntityIdentitySignerVerifier {
    private static final String SECRET = "8YuS04LvRqjpnGnet02bvcdoLIubmcXEt597Gj1rU6bW2MXHvQM90jNnascqF71jsmbp-co91xqE1hie-xKz68BwqAfukX8pGCpXtlzXxrXF_fz46kTcC1HsbvwDzLpxaoAoRKAtEt0onytN4wflPcNvzWjZvAYVcfhb6ydUofU";

    /**
     * Metoda służąca do stworzenia ETagu
     * @param entity Przesyłana encja dla której bedzie tworzony ETag
     * @return Zwracana jest wartość ETagu jeżeli tworzenie się powiedzie oraz "ETag failure" jeżeli nie
     */
    public static String calculateEntitySignature(SignableEntity entity) {
        try {
            JWSSigner signer = new MACSigner(SECRET);
            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(entity.getSignablePayload()));
            jwsObject.sign(signer);
            return jwsObject.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
            return "ETag failure";
        }
    }

    /**
     * Metodda sprawdzająca poprawność ETagu
     * @param tag Wartość ETagu
     * @return Zwracana zostaje wartość reprezentująca czy ETag jest poprawny
     */
    public static boolean validateEntitySignature(String tag) {
        try {
            JWSObject jwsObject = JWSObject.parse(tag);
            JWSVerifier verifier = new MACVerifier(SECRET);
            return jwsObject.verify(verifier);

        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metoda weryfikująca ETag z encją w celu sprawdzenia czy się zgadzają
     * @param tag Wartość ETagu
     * @param entity Encja
     * @return Zwracanie wartości reprezentującej czy encja i ETag się zgadzają
     */
    public static boolean verifyEntityIntegrity(String tag, SignableEntity entity) {
        try {
            final String header = JWSObject.parse(tag).getPayload().toString();
            final String signableEntityPayload = entity.getSignablePayload();
            return validateEntitySignature(tag) && signableEntityPayload.equals(header);

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

}
