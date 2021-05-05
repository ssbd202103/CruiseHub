package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.AccountChangeEmailDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.AdministratorForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ModeratorForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;

import static org.hamcrest.Matchers.containsString;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;


class AccountControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String baseUri = "http://localhost:8080/cruisehub/api/";

    AccountControllerTest() {
    }

    @Test
    public void registerClientTest_SUCCESS() {
        AddressDto address = new AddressDto(1L, "Bortnyka", "30-302", "Pluzhne", "Ukraine");
        ClientForRegistrationDto client = new ClientForRegistrationDto("Artur", "Radiuk", "aradiuk", "aradiuk@gmail.com",
                "123456789", LanguageType.PL, address, "123456789");

        given().baseUri(baseUri).contentType("application/json").body(client).when().post("account/client/registration").then().statusCode(204);
        // todo implement remove method to clean created data
    }

    @Test
    public void registerBusinessWorkerTest_SUCCESS() {
        BusinessWorkerForRegistrationDto businessWorker = new BusinessWorkerForRegistrationDto("Artur", "Radiuk", "aradiuk", "aradiuk@gmail.com",
                "123456789", LanguageType.ENG, "123456789", "FirmaJez");
        given().baseUri(baseUri).contentType("application/json").body(businessWorker).when().post("account/businessworker/registration").then().statusCode(204);
        // todo implement remove method to clean created data
    }

    @Test
    public void registerModeratorTest_SUCCESS() {
        ModeratorForRegistrationDto moderator = new ModeratorForRegistrationDto("Artur", "Radiuk", "aradiuk_moderator", "aradiuk@gmail.com",
                "123456789", LanguageType.ENG);
        given().baseUri(baseUri).contentType("application/json").body(moderator).when().post("account/moderator/registration").then().statusCode(204);
        // todo implement remove method to clean created data
    }

    @Test
    public void registerAdministratorTest_SUCCESS() {
        AdministratorForRegistrationDto administrator = new AdministratorForRegistrationDto("Artur", "Radiuk", "aradiuk_administrator", "aradiuk@gmail.com",
                "123456789", LanguageType.ENG);
        given().baseUri(baseUri).contentType("application/json").body(administrator).when().post("account/administrator/registration").then().statusCode(204);
        // todo implement remove method to clean created data
    }

    private ClientForRegistrationDto getSampleClientForRegistrationDto() {
        AddressDto address = new AddressDto(1L, "Bortnyka", "30-302", "Pluzhne", "Ukraine");
        return new ClientForRegistrationDto("Artur", "Radiuk", randomAlphanumeric(15), randomAlphanumeric(10) + "@gmail.com",
                "123456789", LanguageType.PL, address, "123456789");
    }

    private AccountDto registerClientAndGetAccountDto(ClientForRegistrationDto client) throws JsonProcessingException {
        given().baseUri(baseUri).contentType("application/json").body(client).post("account/client/registration");
        return getAccountDto(client.getLogin());
    }

    private AccountDto getAccountDto(String login) throws JsonProcessingException {
        return objectMapper.readValue(given().baseUri(baseUri).get("account/" + login).thenReturn().asString(), AccountDto.class);
    }

    private RequestSpecification getBaseUriETagRequest(String etag) {
        return given().baseUri(baseUri).header("Etag", etag);
    }

    @Test
    public void changeEmailTest_SUCCESS() throws JsonProcessingException {

        AccountDto account = registerClientAndGetAccountDto(getSampleClientForRegistrationDto());
        Response res = given().baseUri(baseUri).get("account/" + account.getLogin());
        String etag = res.getHeader("Etag");

        AccountChangeEmailDto accountChangeEmailDto = new AccountChangeEmailDto(
                account.getLogin(),
                account.getVersion(),
                "zmienony_dobrze@gmail.com");

        given().baseUri(baseUri).header("If-Match", etag)
                .contentType(ContentType.JSON)
                .body(accountChangeEmailDto)
                .when()
                .put("account/change_email").then().statusCode(204);


        Response changedRes = given().baseUri(baseUri).get("account/" + account.getLogin());
        changedRes.prettyPrint();
        AccountDto changedAccount = objectMapper.readValue(changedRes.thenReturn().asString(), AccountDto.class);

        Assertions.assertEquals(accountChangeEmailDto.getNewEmail(), changedAccount.getEmail());
    }

    @Test
    public void changeEmailTest_FAIL() throws JsonProcessingException {

        AccountDto account = registerClientAndGetAccountDto(getSampleClientForRegistrationDto());
        Response res = given().baseUri(baseUri).get("account/" + account.getLogin());

        AccountChangeEmailDto accountChangeEmailDto = new AccountChangeEmailDto(
                account.getLogin(),
                account.getVersion() - 1,
                "zmieniony_zle@gmail.com");
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(accountChangeEmailDto);

        given().baseUri(baseUri).header("If-Match", etag)
                .contentType(ContentType.JSON)
                .body(accountChangeEmailDto)
                .when()
                .put("account/change_email").then().statusCode(406);
    }
}