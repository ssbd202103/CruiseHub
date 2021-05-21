package pl.lodz.p.it.ssbd2021.ssbd03.security;

import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AuthenticateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.AuthenticateEndpointLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;

/**
 * Klasa udostępniająca API do logowania
 */
@Path("/signin")
@RequestScoped
public class AuthController {

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @Context
    private HttpServletRequest httpServletRequest;

    @Inject
    private AuthenticateEndpointLocal authEndpoint;

    //todo refactor it

    /**
     * Metoda służąca do logowania
     *
     * @param auth Login oraz hasło użytkownika
     * @return Token JWT
     */
    @Path("/auth")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response auth(@Valid @NotNull(message = CONSTRAINT_NOT_NULL) AuthenticateDto auth) throws BaseAppException {
        Credential credential = auth.toCredential();
        CredentialValidationResult result = identityStoreHandler.validate(credential);
        String token;

        if (result.getStatus() != CredentialValidationResult.Status.VALID) {
            try {
                authEndpoint.updateIncorrectAuthenticateInfo(auth.getLogin(), httpServletRequest.getRemoteAddr(), LocalDateTime.now());
            } catch (BaseAppException e) {
                return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).
                        header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .header("Access-Control-Allow-Origin", "*")
                        .build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).entity(I18n.INCORRECT_PASSWORD).header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").header("Access-Control-Allow-Origin", "*")
                    .build();
        }

        token = authEndpoint.updateCorrectAuthenticateInfo(auth.getLogin(), httpServletRequest.getRemoteAddr(), LocalDateTime.now());

        return Response.ok()
                .entity(token)
                .header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }
}