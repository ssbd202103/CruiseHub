package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AuthenticateDto;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {
    private static final String baseUri = "http://localhost:8080/cruisehub/api/";

    @Test
    void auth() {
        AuthenticateDto authInfo = new AuthenticateDto("emusk", "12345678");
        Response response = given().baseUri(baseUri).contentType("application/json").body(authInfo).post("signin/auth");
        assertFalse(response.getBody().asPrettyString().isEmpty());
        assertEquals(response.getStatusCode(), 200);

        AuthenticateDto authInfoFalse = new AuthenticateDto("emusk", "2115");
        Response responseFalse = given().baseUri(baseUri).contentType("application/json").body(authInfoFalse).post("signin/auth");
        assertTrue(responseFalse.getBody().asPrettyString().contains("#badassfish"));
        assertEquals(responseFalse.getStatusCode(), 401);
    }
}