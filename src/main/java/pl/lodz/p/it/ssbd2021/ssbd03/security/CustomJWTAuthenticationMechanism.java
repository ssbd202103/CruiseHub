package pl.lodz.p.it.ssbd2021.ssbd03.security;

import com.auth0.jwt.interfaces.Claim;
import com.nimbusds.jwt.SignedJWT;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.JWTException;

import javax.enterprise.context.RequestScoped;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RequestScoped
public class CustomJWTAuthenticationMechanism implements HttpAuthenticationMechanism {

    public final static String AUTHORIZATION_HEADER = "Authorization";
    public final static String BEARER = "Bearer ";

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) {
        if (request.getRequestURL().toString().endsWith("/api/account/client/registration") ||
                request.getRequestURL().toString().endsWith("/api/account/business-worker/registration") ||
                request.getRequestURL().toString().endsWith("/api/signin/auth")
        ) {
            return httpMessageContext.doNothing();
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {
            return httpMessageContext.responseUnauthorized();
        }

        String jwtSerializedToken = authorizationHeader.substring((BEARER.length())).trim();

        try {
            JWTHandler.validateToken(jwtSerializedToken);
            Map<String, Claim> claims = JWTHandler.getClaimsFromToken(jwtSerializedToken);
            String login = claims.get("login").asString();
            List<String> groups = claims.get("accessLevels").asList(String.class);
            Date expirationTime = claims.get("exp").asDate();
            boolean tokenExpired = new Date().after(expirationTime);
            if (tokenExpired) {
                return httpMessageContext.responseUnauthorized();
            }
            return httpMessageContext.notifyContainerAboutLogin(login, new HashSet<>(groups));
        } catch (JWTException e) {
            e.printStackTrace();
            return httpMessageContext.responseUnauthorized();
        }
    }
}
