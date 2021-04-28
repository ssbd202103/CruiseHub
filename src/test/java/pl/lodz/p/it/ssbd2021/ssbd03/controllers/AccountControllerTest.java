package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.testModel.mok.dto.TestAccountDto;

import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;


class AccountControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String baseUri = "http://localhost:8080/cruisehub/api/account";

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
        BusinessWorkerForRegistrationDto businessWorker = new BusinessWorkerForRegistrationDto("Artur", "Radiuk", "aradiuk", "aradiuk@gmail.com",
                "123456789", LanguageType.ENG, "123456789", "FirmaJez");
        given().baseUri(baseUri).contentType("application/json").body(businessWorker).when().post("businessworker/registration").then().statusCode(204);
        // todo implement remove method to clean created data
    }

    @Test
    void grantAccessLevelTest_SUCCESS() throws JsonProcessingException {
        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        AccountDto account = registerClientAndGetAccountDto(client);
        String etag = getEtagForAccountDto(account);

        Set<AccessLevelType> originalAccessLevels = account.getAccessLevels();
        assertThat(originalAccessLevels).doesNotContain(AccessLevelType.MODERATOR);

        Response response = getBaseUriETagRequest(etag).put("/" + account.getLogin() + "/grantAccessLevel/moderator");
        assertThat(response.getStatusCode()).isEqualTo(200);

        AccountDto updatedAccount = objectMapper.readValue(response.asString(), AccountDto.class);
        assertThat(updatedAccount.getAccessLevels()).contains(AccessLevelType.MODERATOR).containsAll(originalAccessLevels);
        // todo implement remove method to clean created data
    }

    @Test
    void grantAccessLevelTest_FAIL() throws JsonProcessingException {
        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        AccountDto account = registerClientAndGetAccountDto(client);
        String etag = getEtagForAccountDto(account);

        // requesting granting accessLevel with no ETAG
        Response response = given().baseUri(baseUri).put("/" + account.getLogin() + "/grantAccessLevel/moderator");
        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.asString()).isEqualTo(ETAG_EMPTY_ERROR);

        // requesting granting not existing accessLevel
        response = getBaseUriETagRequest(etag).put("/" + account.getLogin() + "/grantAccessLevel/SuperProUser");
        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.asString()).isEqualTo(ACCESS_LEVEL_DOES_NOT_EXIST_ERROR);

        // requesting granting already assigned accessLevel
        response = getBaseUriETagRequest(etag).put("/" + account.getLogin() + "/grantAccessLevel/moderator");
        assertThat(response.getStatusCode()).isEqualTo(200);

        response = getBaseUriETagRequest(etag).put("/" + account.getLogin() + "/grantAccessLevel/moderator");
        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.asString()).isEqualTo(ACCESS_LEVEL_ALREADY_ASSIGNED_ERROR);

        // requesting granting not assignable accessLevel
        response = getBaseUriETagRequest(etag).put("/" + account.getLogin() + "/grantAccessLevel/business_Worker");
        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.asString()).isEqualTo(ACCESS_LEVEL_NOT_ASSIGNABLE_ERROR);
        // todo implement remove method to clean created data
    }

    private ClientForRegistrationDto getSampleClientForRegistrationDto() {
        AddressDto address = new AddressDto(1L, "Bortnyka", "30-302", "Pluzhne", "Ukraine");
        return new ClientForRegistrationDto("Artur", "Radiuk", randomAlphanumeric(15), "aradiuk@gmail.com",
                "123456789", LanguageType.PL, address, "123456789");

    }

    private AccountDto registerClientAndGetAccountDto(ClientForRegistrationDto client) throws JsonProcessingException {
        given().baseUri(baseUri).contentType("application/json").body(client).post("/client/registration");
        return getAccountDto(client.getLogin());
    }

    private AccountDto getAccountDto(String login) throws JsonProcessingException {
        return objectMapper.readValue(given().baseUri(baseUri).get("/" + login).thenReturn().asString(), AccountDto.class);
    }

    private String getEtagForAccountDto(AccountDto account) {
        return EntityIdentitySignerVerifier.calculateEntitySignature(
                new TestAccountDto(account.getLogin(), account.getFirstName(), account.getSecondName(),
                        account.getEmail(), account.getLanguageType(), account.getAccessLevels())
        );
    }

    private RequestSpecification getBaseUriETagRequest(String etag) {
        return given().baseUri(baseUri).header("If-Match", etag);
    }

}