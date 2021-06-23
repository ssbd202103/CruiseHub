package pl.lodz.p.it.ssbd2021.ssbd03.security;

import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.ControllerException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AuthenticateCodeDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AuthenticateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.AccountEndpointLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.AuthenticateEndpointLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.utils.TransactionRepeater.tryAndRepeat;

/**
 * Klasa udostępniająca API do logowania
 */
@Path("/auth")
@RequestScoped
public class AuthController {

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @Context
    private HttpServletRequest httpServletRequest;

    @Inject
    private AuthenticateEndpointLocal authEndpoint;

    @Inject
    private AccountEndpointLocal accountEndpoint;

    /**
     * Metoda służąca do logowania + wysyła kod na e-mail służący do dwufazowego uwierzytelnienia
     *
     * @param auth Login oraz hasło użytkownika
     * @return Odpowiedź http, w przypadku poprawnych danych token
     */
    @Path("/sign-in")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response auth(@Valid @NotNull(message = CONSTRAINT_NOT_NULL) AuthenticateDto auth) throws BaseAppException {
        Credential credential = auth.toCredential();
        CredentialValidationResult result = identityStoreHandler.validate(credential);

        if (result.getStatus() != CredentialValidationResult.Status.VALID) {
            try {
                authEndpoint.updateIncorrectAuthenticateInfo(auth.getLogin(), httpServletRequest.getRemoteAddr(), LocalDateTime.now());
            } catch (BaseAppException e) {
                return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).entity(I18n.INCORRECT_PASSWORD).build();
        }
        authEndpoint.sendAuthenticationCodeEmail(auth);
        return Response.ok().build();
    }

    /**
     * Odswieza token uzytkownika
     * @param tokenString Token
     * @return Odswiezony token
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    @POST
    @Path("/refresh-token/")
    @Produces(MediaType.TEXT_PLAIN)
    public String refreshJWTToken(@HeaderParam("Authorization") String tokenString) throws BaseAppException {
        if (!tokenString.contains("Bearer ")) {
            throw new ControllerException("Invalid authorization header");
        }
        String token = tokenString.substring("Bearer ".length());
        return tryAndRepeat(authEndpoint, () -> authEndpoint.refreshToken(token));
    }


    /**
     * Stwórz nowe konto z poziomem dostępu Klient
     *
     * @param clientForRegistrationDto Zbiór danych niezbędnych dla stworzenia konta z poziomem dostępu Klient
     */
    @POST
    @Path("/client/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createClient(@Valid @NotNull(message = CONSTRAINT_NOT_NULL) ClientForRegistrationDto clientForRegistrationDto) throws BaseAppException {
        tryAndRepeat(accountEndpoint, () -> accountEndpoint.createClientAccount(clientForRegistrationDto));
    }

    /**
     * Stwórz nowe konto z poziomem dostępu Pracownik firmy
     *
     * @param businessWorkerForRegistrationDto Zbiór danych niezbędnych dla stworzenia konta z poziomem dostępu Pracownik firmy
     */
    @POST
    @Path("/business-worker/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createBusinessWorker(@Valid @NotNull(message = CONSTRAINT_NOT_NULL) BusinessWorkerForRegistrationDto businessWorkerForRegistrationDto) throws BaseAppException {
        tryAndRepeat(accountEndpoint, () -> accountEndpoint.createBusinessWorkerAccount(businessWorkerForRegistrationDto));
    }

    /**
     * Metoda służąca do logowania przy użyciu kodu wysłanego przy pierwszej fazie logowania
     *
     * @param auth Login oraz kod wysłany w emailu
     * @return Token JWT
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    @Path("/code-sign-in")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String doubleAuth(@Valid @NotNull(message = CONSTRAINT_NOT_NULL) AuthenticateCodeDto auth) throws BaseAppException {
        return authEndpoint.authWCodeUpdateCorrectAuthenticateInfo(auth.getLogin(), auth.getCode(),  httpServletRequest.getRemoteAddr(), LocalDateTime.now());
    }
}