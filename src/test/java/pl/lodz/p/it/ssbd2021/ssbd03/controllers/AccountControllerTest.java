package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDtoForList;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.GrantAccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;

import java.util.Set;


import java.io.DataInput;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;


class AccountControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String baseUri = "http://localhost:8080/api/account";

    AccountControllerTest() {
    }

    @Test
    public void registerClientTest_SUCCESS() {
        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        given().baseUri(baseUri).contentType("application/json").body(client).when().post("/client/registration").then().statusCode(204);
        // todo implement remove method to clean created data
    }

    @Test
    public void registerBusinessWorkerTest_SUCCESS() {
        BusinessWorkerForRegistrationDto businessWorker = new BusinessWorkerForRegistrationDto("Artur", "Radiuk", randomAlphanumeric(15), randomAlphanumeric(10) + "@gmail.com",
                "123456789", LanguageType.ENG, "123456789", "FirmaJez");
        given().baseUri(baseUri).contentType("application/json").body(businessWorker).when().post("/businessworker/registration").then().statusCode(204);
        // todo implement remove method to clean created data
    }

    @Test
    void grantAccessLevelTest_SUCCESS() throws JsonProcessingException {
        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        AccountDto account = registerClientAndGetAccountDto(client);
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(account);

        Set<AccessLevelType> originalAccessLevels = account.getAccessLevels();
        assertThat(originalAccessLevels).doesNotContain(AccessLevelType.MODERATOR);

        GrantAccessLevelDto grantAccessLevel = new GrantAccessLevelDto(account.getLogin(), AccessLevelType.MODERATOR, account.getVersion());

        Response response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).body(grantAccessLevel).put("/grantAccessLevel");
        assertThat(response.getStatusCode()).isEqualTo(200);

        AccountDto updatedAccount = objectMapper.readValue(response.asString(), AccountDto.class);
        assertThat(updatedAccount.getAccessLevels()).contains(AccessLevelType.MODERATOR).containsAll(originalAccessLevels);
        // todo implement remove method to clean created data
    }

    @Test
    void grantAccessLevelTest_FAIL() throws JsonProcessingException {
        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        AccountDto account = registerClientAndGetAccountDto(client);
        GrantAccessLevelDto grantAccessLevel = new GrantAccessLevelDto(account.getLogin(), AccessLevelType.MODERATOR, account.getVersion());
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(account);

        // requesting granting accessLevel with no ETAG
        Response response = given().baseUri(baseUri).contentType(ContentType.JSON).body(grantAccessLevel).put("/grantAccessLevel");
        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.asString()).isEqualTo(ETAG_EMPTY_ERROR);

        // requesting granting already assigned accessLevel
        response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).body(grantAccessLevel).put("/grantAccessLevel");
        assertThat(response.getStatusCode()).isEqualTo(200);


        response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).body(grantAccessLevel).put("/grantAccessLevel");
        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.asString()).isEqualTo(ACCESS_LEVEL_ALREADY_ASSIGNED_ERROR);

        // requesting granting not assignable accessLevel
        grantAccessLevel.setAccessLevel(AccessLevelType.BUSINESS_WORKER);

        response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).body(grantAccessLevel).put("/grantAccessLevel");
        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.asString()).isEqualTo(ACCESS_LEVEL_NOT_ASSIGNABLE_ERROR);

        // todo check for optimistic lock once working mechanism is implemented correctly
        // todo implement remove method to clean created data
    }

    @Test
    public void getAllAccountsTest_SUCCESS() throws JsonProcessingException {
        Response response = RestAssured.given().header("Content-Type", "application/json").baseUri(baseUri).get("/accounts");
        String accountString = response.getBody().asString();
        List<AccountDtoForList> accountDtoList = Arrays.asList(objectMapper.readValue(accountString, AccountDtoForList[].class));

        int numberOfUsers = accountDtoList.size();
        ClientForRegistrationDto client = getSampleClientForRegistrationDto();

        assertTrue(accountDtoList.stream().noneMatch(account -> account.getLogin().equals(client.getLogin())));

        AccountDto account = registerClientAndGetAccountDto(client);

        response = RestAssured.given().contentType(ContentType.JSON).baseUri(baseUri).get("/accounts");

        accountString = response.getBody().asString();
        accountDtoList = Arrays.asList(objectMapper.readValue(accountString, AccountDtoForList[].class));

        int numberOfUsersAfterChanges = accountDtoList.size();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertEquals(numberOfUsers + 1, numberOfUsersAfterChanges);

        assertTrue(accountDtoList.stream().anyMatch(newAccount -> newAccount.getLogin().equals(account.getLogin())));

    }

    @Test
    public void unblockUserTest_SUCCESS() throws JsonProcessingException, ParseException {
        Response response = given().header("Content-Type", "application/json").baseUri(baseUri).get("/accounts");
        String accountString = response.getBody().asString();
        List<AccountDtoForList> accountDtoList = Arrays.asList(objectMapper.readValue(accountString, AccountDtoForList[].class));
        String etag = accountDtoList.stream().filter(acc -> acc.getLogin().equals("ZloG1kjXy8mxuLU")).findFirst().get().getEtag();
        JSONParser toJson = new JSONParser();
        String admin = "rbranson";
        Response postResponse = given().contentType(ContentType.JSON).header("If-Match", etag).baseUri(baseUri).body(toJson.parse(admin)).put("/unblock/ZloG1kjXy8mxuLU");

        response = given().header("Content-Type", "application/json").baseUri(baseUri).get("/accounts");
        accountString = response.getBody().asString();
        accountDtoList = Arrays.asList(objectMapper.readValue(accountString, AccountDtoForList[].class));
        AccountDtoForList accountDtoForList = accountDtoList.stream().filter(acc -> acc.getLogin().equals("ZloG1kjXy8mxuLU")).findFirst().get();

        assertEquals(false, accountDtoForList.isActive());


    }



    private ClientForRegistrationDto getSampleClientForRegistrationDto() {
        AddressDto address = new AddressDto(1L, "Bortnyka", "30-302", "Pluzhne", "Ukraine");
        return new ClientForRegistrationDto("Artur", "Radiuk", randomAlphanumeric(15), randomAlphanumeric(10) + "@gmail.com",
                "123456789", LanguageType.PL, address, "123456789");
    }

    private AccountDto registerClientAndGetAccountDto(ClientForRegistrationDto client) throws JsonProcessingException {
        given().baseUri(baseUri).contentType("application/json").body(client).post("/client/registration");
        return getAccountDto(client.getLogin());
    }

    private AccountDto getAccountDto(String login) throws JsonProcessingException {
        return objectMapper.readValue(given().baseUri(baseUri).get("/" + login).thenReturn().asString(), AccountDto.class);
    }

    private RequestSpecification getBaseUriETagRequest(String etag) {
        return given().baseUri(baseUri).header("If-Match", etag);
    }
}