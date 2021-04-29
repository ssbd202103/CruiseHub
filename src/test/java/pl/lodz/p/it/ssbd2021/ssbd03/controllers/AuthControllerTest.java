package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AuthenticateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.security.AuthController;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {
    private static final String baseUri = "http://localhost:8080/cruisehub/api/";

    @Test
    void auth() {
        AuthenticateDto authInfo = new AuthenticateDto("emusk", "12345678");
        given().baseUri(baseUri).contentType("application/json").body(authInfo).when().post("signin/auth").then().statusCode(200);
    }
}