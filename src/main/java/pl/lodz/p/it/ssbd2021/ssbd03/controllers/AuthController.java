package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AuthenticateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.AuthenticateEndpointLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Klasa udostępniająca API do logowania
 */
@Path("/signin")
@RequestScoped
public class AuthController {

    @EJB
    private AuthenticateEndpointLocal authEndpoint;

    /**
     * Metoda służąca do logowania
     * @param auth Login oraz hasło użytkownika
     * @return Token JWT
     */
    @POST
//    @Path() if case of need uncomment and use
    @Consumes(MediaType.APPLICATION_JSON)
    public Response auth(@Valid @NotNull AuthenticateDto auth) {
        return authEndpoint.authenticate(auth.getLogin(), auth.getPassword());
    }
}