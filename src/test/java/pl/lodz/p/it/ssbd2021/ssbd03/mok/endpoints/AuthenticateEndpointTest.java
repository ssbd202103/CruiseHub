package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;

import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticateEndpointTest {

    @Test
    void authenticate() {
        String login = "rbranson";
        String password = "12345678";
        AuthenticateEndpoint auth = new AuthenticateEndpoint();
        Response response = auth.authenticate(login, password);

    }
}