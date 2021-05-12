package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AuthenticateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.converters.AccountMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.security.JWTHandler;

import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.jupiter.api.Assertions.*;

class AuthControllerIT {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String baseUri = "http://localhost:8080/cruisehub/api/";

    @Test
    void auth() throws JsonProcessingException {
        ClientForRegistrationDto sampleClient = getSampleClientForRegistrationDto();
        ClientForRegistrationDto account = registerClientAndGetAccountDto(sampleClient);
        prepareUser(account);

        String correctLogin = "mlTHl5T3LjSAObh";
        String correctPassword = "123456789";


        System.out.println(account.getLogin());


        // correct test
        AuthenticateDto authInfo = new AuthenticateDto(correctLogin, correctPassword);
        Response response = given().baseUri(baseUri).contentType("application/json").body(authInfo).post("signin/auth");
        assertFalse(response.getBody().asPrettyString().isEmpty());
        assertEquals(200, response.getStatusCode());

        // a test with an incorrect password
        AuthenticateDto authInfoFalse = new AuthenticateDto(correctLogin, "IncorrectPassword");
        Response responseFalse = given().baseUri(baseUri).contentType("application/json").body(authInfoFalse).post("signin/auth");
        assertTrue(responseFalse.getBody().asPrettyString().contains("Incorrect password"));
        assertEquals(401, responseFalse.getStatusCode());

        // a test with an incorrect login
        AuthenticateDto authInfoFalse2 = new AuthenticateDto("IncorrectLogin", "AndARandomPassword");
        Response responseFalse2 = given().baseUri(baseUri).contentType("application/json").body(authInfoFalse2).post("signin/auth");
        assertTrue(responseFalse2.getBody().asPrettyString().contains("Incorrect login"));
        assertEquals(401, responseFalse2.getStatusCode());
    }

    private ClientForRegistrationDto getSampleClientForRegistrationDto() {
        AddressDto address = new AddressDto(1L, "Bortnyka", "30-302", "Pluzhne", "Ukraine");
        return new ClientForRegistrationDto("Artur", "Radiuk", randomAlphanumeric(15), randomAlphanumeric(10) + "@gmail.com",
            "12345678", LanguageType.PL, address, "123456789");
    }

    private ClientForRegistrationDto registerClientAndGetAccountDto(ClientForRegistrationDto client) {
        given().baseUri("http://localhost:8080/cruisehub/api/account").contentType("application/json").body(client).post("/client/registration");
        return client;
    }

    private void prepareUser(ClientForRegistrationDto client) {
        Account account = AccountMapper.extractAccountFromClientForRegistrationDto(client);

        Map<String, Object> map = Map.of("login", account.getLogin(), "accessLevels", account.getAccessLevels()
            .stream().map(accessLevel -> accessLevel.getAccessLevelType().name()).collect(Collectors.toList()));
        String token = JWTHandler.createToken(map, account.getId().toString());

        // confirm
        given().baseUri("http://localhost:8080/cruisehub/api/account").contentType("application/json").body(token).put("/account-verification");
    }
}