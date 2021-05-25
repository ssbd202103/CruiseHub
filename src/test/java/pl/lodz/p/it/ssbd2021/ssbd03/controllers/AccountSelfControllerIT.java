package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.ETagException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AuthenticateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.AccountChangeOwnPasswordDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.ChangeModeDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;

import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.jupiter.api.Assertions.*;

class AccountSelfControllerIT {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String selfBaseUri;
    private final String accountBaseUri;
    private final String authBaseUri;
    AccountSelfControllerIT() {
        Properties securityProperties = PropertiesReader.getSecurityProperties();
        selfBaseUri  = securityProperties.getProperty("app.baseurl") + "/api/self";
        accountBaseUri = securityProperties.getProperty("app.baseurl") + "/api/account";
        authBaseUri = securityProperties.getProperty("app.baseurl") + "/api/auth";
    }

    @Test
    public void changePasswordTest_SUCCESS() throws JsonProcessingException {
        String adminToken = this.getAuthToken("rbranson", "abcABC123*");
        AccountDto account = registerClientAndGetAccountDto(getSampleClientForRegistrationDto());
        Response res = given().relaxedHTTPSValidation().baseUri(accountBaseUri).header(new Header("Authorization", "Bearer " + adminToken)).get("/" + account.getLogin());
        String etag = res.getHeader("Etag");

        AccountChangeOwnPasswordDto accountChangeOwnPasswordDto = new AccountChangeOwnPasswordDto(
                account.getLogin(),
                account.getVersion(),
                "abcABC123*",
                "ABCabc123*"
        );

        given().relaxedHTTPSValidation().baseUri(selfBaseUri).header("If-Match", etag)
                .contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken))
                .body(accountChangeOwnPasswordDto)
                .when()
                .put("change-password").then().statusCode(204);

        Response changedRes = given().relaxedHTTPSValidation().baseUri(accountBaseUri).header(new Header("Authorization", "Bearer " + adminToken)).get("/" + account.getLogin());
        AccountDto changedAccount = objectMapper.readValue(changedRes.thenReturn().asString(), AccountDto.class);
        assertTrue(changedAccount.getVersion() > account.getVersion());
        // potrzebna jest metoda do logowania, by sprawdzic zmienianie hasla
    }

    @Test
    public void changePasswordVersionTest_FAIL() throws JsonProcessingException {
        // fail optimistic check (version)
        String adminToken = this.getAuthToken("rbranson", "abcABC123*");
        AccountDto account = registerClientAndGetAccountDto(getSampleClientForRegistrationDto());
        Response res = given().relaxedHTTPSValidation().baseUri(accountBaseUri).header(new Header("Authorization", "Bearer " + adminToken)).get("/" + account.getLogin());
        String etag = res.getHeader("Etag");

        AccountChangeOwnPasswordDto accountChangeOwnPasswordDto = new AccountChangeOwnPasswordDto(
                account.getLogin(),
                account.getVersion() + 1,
                "abcABC123*",
                "ABCabc123*"
        );

        given().relaxedHTTPSValidation().baseUri(selfBaseUri).header("If-Match", etag)
                .contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken))
                .body(accountChangeOwnPasswordDto)
                .when()
                .put("change-password").then().statusCode(400);

        Response notChangedRes = given().relaxedHTTPSValidation().baseUri(accountBaseUri).header(new Header("Authorization", "Bearer " + adminToken)).get("/" + account.getLogin());
        AccountDto notChangedAccount = objectMapper.readValue(notChangedRes.thenReturn().asString(), AccountDto.class);
        Assertions.assertEquals(account.getVersion(), notChangedAccount.getVersion());
    }

    @Test
    public void changePasswordMatchTest_FAIL() throws JsonProcessingException {
        // fail old password check
        String adminToken = this.getAuthToken("rbranson", "abcABC123*");
        AccountDto account = registerClientAndGetAccountDto(getSampleClientForRegistrationDto());
        Response res = given().relaxedHTTPSValidation().baseUri(accountBaseUri).header(new Header("Authorization", "Bearer " + adminToken)).get("/" + account.getLogin());
        String etag = res.getHeader("Etag");

        AccountChangeOwnPasswordDto accountChangeOwnPasswordDto = new AccountChangeOwnPasswordDto(
                account.getLogin(),
                account.getVersion(),
                "CBAcba123*",
                "ABCabc123*"
        );

        given().relaxedHTTPSValidation().baseUri(selfBaseUri).header("If-Match", etag)
                .contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken))
                .body(accountChangeOwnPasswordDto)
                .when()
                .put("change-password").then().statusCode(400);

        Response notChangedRes = given().relaxedHTTPSValidation().baseUri(accountBaseUri).header(new Header("Authorization", "Bearer " + adminToken)).get("/" + account.getLogin());
        AccountDto notChangedAccount = objectMapper.readValue(notChangedRes.thenReturn().asString(), AccountDto.class);
        Assertions.assertEquals(account.getVersion(), notChangedAccount.getVersion());
    }

    @Test
    public void changeModeTest_SUCCESS() throws JsonProcessingException, ETagException {
        AccountDto account = registerClientAndGetAccountDto(getSampleClientForRegistrationDto());
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(account);
        ChangeModeDto changeModeDto = new ChangeModeDto(account.getLogin(), account.getVersion(), true);
        String adminToken = this.getAuthToken("rbranson", "abcABC123*");

        given().relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .baseUri(selfBaseUri)
                .header(new Header("If-Match", etag))
                .header(new Header("Authorization", "Bearer " + adminToken))
                .body(changeModeDto)
                .put("/change-theme-mode")
                .then().statusCode(204);

        AccountDto changedAccount = getAccountDto(account.getLogin());

        assertTrue(changedAccount.isDarkMode());
    }

    private ClientForRegistrationDto getSampleClientForRegistrationDto() {
        AddressDto address = new AddressDto(1L, "Bortnyka", "30-302", "Pluzhne", "Ukraine");
        return new ClientForRegistrationDto("Artur", "Radiuk", randomAlphanumeric(15), randomAlphanumeric(10) + "@gmail.com",
                "abcABC123*", LanguageType.PL, address, "123456789");
    }

    private AccountDto registerClientAndGetAccountDto(ClientForRegistrationDto client) throws JsonProcessingException {
        given().relaxedHTTPSValidation().baseUri(authBaseUri).contentType("application/json").body(client).post("/client/registration");
        return getAccountDto(client.getLogin());
    }
    private String getAuthToken(String login, String password) {
        AuthenticateDto authenticateDto = new AuthenticateDto(login, password);

        Response response = given().relaxedHTTPSValidation().baseUri(authBaseUri)
                .contentType(ContentType.JSON)
                .body(authenticateDto)
                .when()
                .post("/sign-in");
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        return response.getBody().asString();
    }
    private AccountDto getAccountDto(String login) throws JsonProcessingException {
        String authToken = this.getAuthToken("rbranson", "abcABC123*");
        return objectMapper.readValue(given().relaxedHTTPSValidation().baseUri(accountBaseUri).header(new Header("Authorization", "Bearer " + authToken)).get("/" + login).thenReturn().asString(), AccountDto.class);
    }
}
