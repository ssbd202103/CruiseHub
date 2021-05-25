package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AuthenticateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;

import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class AuthControllerIT {
    private final String authBaseUri;

    AuthControllerIT() {
        Properties securityProperties = PropertiesReader.getSecurityProperties();
        authBaseUri = securityProperties.getProperty("app.baseurl") + "/api/auth";
    }

    @Test
    void auth() {
        String correctLogin = "emusk";
        String correctPassword = "abcABC123*";

        // correct test
        AuthenticateDto authInfo = new AuthenticateDto(correctLogin, correctPassword);
        Response response = given().relaxedHTTPSValidation().baseUri(authBaseUri).contentType("application/json").body(authInfo).post("/sign-in");
        assertFalse(response.getBody().asPrettyString().isEmpty());
        assertEquals(200, response.getStatusCode());

        // a test with an incorrect password
        AuthenticateDto authInfoFalse = new AuthenticateDto(correctLogin, "Incorre2*ctPassword");
        Response responseFalse = given().relaxedHTTPSValidation().baseUri(authBaseUri).contentType("application/json").body(authInfoFalse).post("/sign-in");
        assertEquals(401, responseFalse.getStatusCode());
    }
}