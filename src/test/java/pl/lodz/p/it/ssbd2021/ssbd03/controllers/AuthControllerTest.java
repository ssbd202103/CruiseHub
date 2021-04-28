package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AuthenticateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.security.AuthController;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {

    @Test
    void auth() throws Exception {
        AuthController auth = new AuthController();

        AuthenticateDto authInfo = new AuthenticateDto("rbranson", "12345678");


        Response response = auth.auth(authInfo);

        assertEquals(response.getStatus(), 200);
    }
}