package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.PasswordResetDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.GrantAccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;

import javax.ws.rs.core.MediaType;
import java.util.Set;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;


class AccountControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String baseUri = "http://localhost:8080/api/account";

    AccountControllerTest() {
    }

    @Test
    public void requestPasswordReset_SUCCESS() throws JsonProcessingException {
        AccountDto accountDto = registerClientAndGetAccountDto(getSampleClientForRegistrationDto());
        given().baseUri(baseUri).contentType(MediaType.APPLICATION_JSON).post("/request-password-reset/" + accountDto.getLogin()).then().statusCode(200);
    }

    @Test
    @Disabled
    // Potrzebna jest metoda to potwierdzenia konta żeby przeprowadzić logowania do nowoutworzonego użytkownika dla którego w teście będzie zmieniane hasło
    public void resetPassword_SUCCESS() {
        PasswordResetDto passwordResetDto = new PasswordResetDto(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcmFkaXVrIiwiaXNzIjoiY3J1aXNlaHViIiwiZXhwIjoxNjE5ODU4NDA3LCJ2ZXJzaW9uIjo1LCJpYXQiOjE2MTk4NTcyMDcsImp0aSI6Ijk0M2EyMzM1LTFlZTctNGE4ZS1iNmFhLWU3Y2FiM2I3OWNmYSJ9.wI1xu7qZDCVrV-HYGCxgqbn9HVIr8mFW0JC0g_qZn3A",
                "aradiuk", "password");
        given().baseUri(baseUri).contentType(MediaType.APPLICATION_JSON).body(passwordResetDto).post("/reset-password").then().statusCode(200);
    }

    @Test
    public void registerClientTest_SUCCESS() {
        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        given().baseUri(baseUri).contentType(MediaType.APPLICATION_JSON).body(client).when().post("/client/registration").then().statusCode(204);
        // todo implement remove method to clean created data
    }

    @Test
    public void registerBusinessWorkerTest_SUCCESS() {
        BusinessWorkerForRegistrationDto businessWorkerDto = new BusinessWorkerForRegistrationDto("Artur", "Radiuk", randomAlphanumeric(15), randomAlphanumeric(10) + "@gmail.com",
                "123456789", LanguageType.ENG, "123456789", "FirmaJez");
        AccountDto accountDto = new AccountDto(businessWorkerDto.getLogin(), businessWorkerDto.getFirstName(), businessWorkerDto.getSecondName(),
                businessWorkerDto.getEmail(), LanguageType.ENG, Set.of(AccessLevelType.BUSINESS_WORKER), 0l);
        given().baseUri(baseUri).contentType(MediaType.APPLICATION_JSON).body(businessWorkerDto).when().post("/business-worker/registration").then().statusCode(204);
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
    public void getAllAccountsTest_SUCCESS() {
        Response response = null;
        List<AccountDto> accountDtoList = null;
        try {
            response = RestAssured.given().header("Content-Type", "application/json").baseUri(baseUri).get(new URI("account/accounts"));
            String accountString = response.getBody().asString();
            ObjectMapper mapper = new ObjectMapper();
            accountDtoList = Arrays.asList(mapper.readValue(accountString, AccountDto[].class));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        assertEquals(response.getStatusCode(), javax.ws.rs.core.Response.Status.OK.getStatusCode());
        assertEquals(accountDtoList.get(0).getEmail(), "rbranson@gmail.com");
        assertEquals(accountDtoList.get(1).getEmail(), "emusk@gmail.com");
        assertEquals(accountDtoList.get(2).getEmail(), "jbezos@gmail.com");
        assertEquals(accountDtoList.get(0).getFirstName(), "Richard");
        assertEquals(accountDtoList.get(0).getSecondName(), "Branson");


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