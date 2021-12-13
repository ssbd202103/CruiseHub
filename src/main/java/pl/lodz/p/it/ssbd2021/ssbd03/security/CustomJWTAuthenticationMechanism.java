package pl.lodz.p.it.ssbd2021.ssbd03.security;

import com.auth0.jwt.interfaces.Claim;
import org.yaml.snakeyaml.Yaml;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.JWTException;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;

import javax.enterprise.context.RequestScoped;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Klasa zapewniajaca mechanizm do uwierzytelniania z tokenem jwt
 */
@RequestScoped
public class CustomJWTAuthenticationMechanism implements HttpAuthenticationMechanism {

    public final static String AUTHORIZATION_HEADER = "Authorization";
    public final static String BEARER = "Bearer ";
    private final static Map<String, List<String>> ROLES_MAP = new Yaml().load(CustomJWTAuthenticationMechanism.class.getClassLoader()
            .getResourceAsStream("security/roles.yaml"));

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) {
        if (
                !request.getRequestURL().toString().contains("/api/") ||
                        request.getRequestURL().toString().matches("^.*/(?:(?:registration|(?:code-)?sign-in|attractions/cruise/.*|verify|kill/health|reset-password|cruise/cruise_group/.*|get-cruise/.*|cruises(?:-for-group/.*)?|companies-info)(?:\\?.+)?|request-password-reset.+)$")
                        || request.getMethod().equals("OPTIONS")
        ) {
            return CORS(CORS(httpMessageContext)).doNothing();
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {
            return CORS(httpMessageContext).responseUnauthorized();
        }

        String jwtSerializedToken = authorizationHeader.substring((BEARER.length())).trim();

        try {
            JWTHandler.validateToken(jwtSerializedToken);
            Map<String, Claim> claims = JWTHandler.getClaimsFromToken(jwtSerializedToken);
            String login = claims.get("sub").asString();
            List<String> groups = claims.get("accessLevels").asList(String.class);
            Date expirationTime = claims.get("exp").asDate();
            boolean tokenExpired = new Date().after(expirationTime);
            if (tokenExpired) {
                return CORS(httpMessageContext).responseUnauthorized();
            }
            return CORS(httpMessageContext).notifyContainerAboutLogin(login, getGroupsForRoles(groups));
        } catch (JWTException e) {
            e.printStackTrace();
            return CORS(httpMessageContext).responseUnauthorized();
        }
    }

    private HttpMessageContext CORS(HttpMessageContext context) {
        context.getResponse().setHeader("Access-Control-Allow-Origin", "https://localhost:555");
        context.getResponse().setHeader("Access-Control-Allow-Headers", "*");
        context.getResponse().setHeader("Access-Control-Allow-Credentials", "true");
        context.getResponse().setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
        return context;
    }

    private static Set<String> getGroupsForRoles(List<String> roles) {
        return Stream.of(roles.stream(),
                        roles.stream().flatMap(role -> ROLES_MAP.getOrDefault(role, Collections.emptyList()).stream())
                ).flatMap(it -> it)
                .collect(Collectors.toSet());
    }
}
