package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AuthenticateDto;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class AuthControllerIT {
    private static final String baseUri = "http://localhost:8080/api/";

    @Test
    void auth() {
        String correctLogin = "emusk";
        String correctPassword = "12345678";

        // correct test
        AuthenticateDto authInfo = new AuthenticateDto(correctLogin, correctPassword);
        Response response = given().baseUri(baseUri).contentType("application/json").body(authInfo).post("signin/auth");
        assertFalse(response.getBody().asPrettyString().isEmpty());
        assertEquals(200, response.getStatusCode());

        // a test with an incorrect password
        AuthenticateDto authInfoFalse = new AuthenticateDto(correctLogin, "IncorrectPassword");
        Response responseFalse = given().baseUri(baseUri).contentType("application/json").body(authInfoFalse).post("signin/auth");
        assertEquals(401, responseFalse.getStatusCode());

        // a test with an incorrect login
        AuthenticateDto authInfoFalse2 = new AuthenticateDto("IncorrectLogin", "AndARandomPassword");
        Response responseFalse2 = given().baseUri(baseUri).contentType("application/json").body(authInfoFalse2).post("signin/auth");
        assertEquals(401, responseFalse2.getStatusCode());
    }
}