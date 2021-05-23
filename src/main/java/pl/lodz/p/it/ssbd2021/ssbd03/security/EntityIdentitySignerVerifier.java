package pl.lodz.p.it.ssbd2021.ssbd03.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.ETagException;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.interceptor.Interceptors;
import java.text.ParseException;
import java.util.Properties;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.ETAG_CREATION_ERROR;

/**
 * Klasa udostępniająca statyczne metody obsługujące ETag
 */
@Interceptors(TrackingInterceptor.class)
public class EntityIdentitySignerVerifier {
    private static final Properties securityProperties = PropertiesReader.getSecurityProperties();

    /**
     * Metoda służąca do stworzenia ETagu
     *
     * @param entity Przesyłana encja dla której bedzie tworzony ETag
     * @return Zwracana jest wartość ETagu jeżeli tworzenie się powiedzie oraz "ETag failure" jeżeli nie
     */
    public static String calculateEntitySignature(SignableEntity entity) throws ETagException {
        try {
            JWSSigner signer = new MACSigner(securityProperties.getProperty("etag.secret"));

            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(entity.getSignablePayload()));
            jwsObject.sign(signer);
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new ETagException(ETAG_CREATION_ERROR, e);
        }
    }

    /**
     * Metodda sprawdzająca poprawność ETagu
     *
     * @param tag Wartość ETagu
     * @return Zwracana zostaje wartość reprezentująca czy ETag jest poprawny
     */
    public static boolean validateEntitySignature(String tag) {
        try {
            JWSObject jwsObject = JWSObject.parse(tag);
            JWSVerifier verifier = new MACVerifier(securityProperties.getProperty("etag.secret"));
            return jwsObject.verify(verifier);

        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metoda weryfikująca ETag z encją w celu sprawdzenia czy się zgadzają
     *
     * @param tag    Wartość ETagu
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
