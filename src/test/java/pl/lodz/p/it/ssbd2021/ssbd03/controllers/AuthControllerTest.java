package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AuthenticateDto;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {
    private static final String baseUri = "http://localhost:8080/api/";

    @Test
    void auth() {
        // correct test
        AuthenticateDto authInfo = new AuthenticateDto("emusk", "12345678");
        Response response = given().baseUri(baseUri).contentType("application/json").body(authInfo).post("signin/auth");
        assertFalse(response.getBody().asPrettyString().isEmpty());
        assertEquals(response.getStatusCode(), 200);

        // a test with an incorrect password
        AuthenticateDto authInfoFalse = new AuthenticateDto("emusk", "IncorrectPassword");
        Response responseFalse = given().baseUri(baseUri).contentType("application/json").body(authInfoFalse).post("signin/auth");
        assertTrue(responseFalse.getBody().asPrettyString().contains("#badassfish"));
        assertEquals(responseFalse.getStatusCode(), 401);

        // a test with an incorrect login
        AuthenticateDto authInfoFalse2 = new AuthenticateDto("IncorrectLogin", "AndARandomPassword");
        Response responseFalse2 = given().baseUri(baseUri).contentType("application/json").body(authInfoFalse2).post("signin/auth");
        assertTrue(responseFalse2.getBody().asPrettyString().contains("#badassfish"));
        assertEquals(responseFalse2.getStatusCode(), 401);
    }
}