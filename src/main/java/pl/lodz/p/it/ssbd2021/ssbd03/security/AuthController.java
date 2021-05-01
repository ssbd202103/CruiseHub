package pl.lodz.p.it.ssbd2021.ssbd03.security;

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

    /**
     * Metoda służąca do logowania
     * @param auth Login oraz hasło użytkownika
     * @return Token JWT
     */
    @Path("/auth")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response auth(@Valid @NotNull AuthenticateDto auth) throws Exception {
        Credential fiut = auth.toCredential();
        CredentialValidationResult result = identityStoreHandler.validate(fiut);
        String token;

        if (result.getStatus() != CredentialValidationResult.Status.VALID) {
            authEndpoint.updateIncorrectAuthenticateInfo(auth.getLogin(), httpServletRequest.getRemoteAddr(), LocalDateTime.now());
            return Response.status(Response.Status.UNAUTHORIZED)
                .build();
        }

        token = authEndpoint.updateCorrectAuthenticateInfo(auth.getLogin(), httpServletRequest.getRemoteAddr(), LocalDateTime.now());

        return Response.ok()
            .entity(token)
            .build();
    }
}