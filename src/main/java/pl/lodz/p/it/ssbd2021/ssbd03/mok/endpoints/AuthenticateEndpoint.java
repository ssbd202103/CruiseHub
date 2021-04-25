package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;

import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AuthenticateResponse;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.managers.AccountManagerLocal;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

/**
 * Klasa która zajmuje się uwierzytelnieniem użytkownika.
 */
@Stateful
public class AuthenticateEndpoint implements AuthenticateEndpointLocal {

    @EJB
    private AccountManagerLocal accountManager;

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response authenticate(@NotNull String login, @NotNull String password) {

        String passwordHash = Arrays.toString(DigestUtils.sha256(password));

        AuthenticateResponse response = accountManager.authenticate(login, passwordHash);

        if (response.getResponseStatus() == Response.Status.OK) {
            return Response.status(Response.Status.OK)
                    .type("application/jwt")
                    .entity(response.getToken())
                    .build();
        } else {
            return Response.status(response.getResponseStatus())
                    .build();
        }
    }
}
