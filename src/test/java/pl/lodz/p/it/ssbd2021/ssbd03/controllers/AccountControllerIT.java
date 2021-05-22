package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.ETagException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.ChangeAccessLevelStateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.GrantAccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccessLevelDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccountDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;

import javax.json.Json;
import javax.ws.rs.core.MediaType;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;


class AccountControllerIT {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String accountBaseUri;
    private final String authBaseUri;

    AccountControllerIT() {
        Properties securityProperties = PropertiesReader.getSecurityProperties();
        accountBaseUri = securityProperties.getProperty("app.baseurl") + "/api/account";
        authBaseUri = securityProperties.getProperty("app.baseurl") + "/api/auth";
    }

    @Test
    public void requestPasswordReset_SUCCESS() throws JsonProcessingException {
        AccountDto accountDto = registerClientAndGetAccountDto(getSampleClientForRegistrationDto());
        given().baseUri(accountBaseUri).contentType(MediaType.APPLICATION_JSON).post("/request-password-reset/" + accountDto.getLogin()).then().statusCode(204);
    }

    @Test
    public void requestSomeonesPasswordReset_SUCCESS() throws JsonProcessingException {
        String adminToken = this.getAuthToken("rbranson", "abcABC123*");
        AccountDto accountDto = registerClientAndGetAccountDto(getSampleClientForRegistrationDto());
        given().baseUri(accountBaseUri).contentType(MediaType.APPLICATION_JSON).header(new Header("Authorization", "Bearer " + adminToken)).post("/request-someones-password-reset/" + accountDto.getLogin() + "/" + accountDto.getEmail()).then().statusCode(204);
    }


    @Test // todo
    @Disabled
    // Potrzebna jest metoda to potwierdzenia konta żeby przeprowadzić logowania do nowoutworzonego użytkownika dla którego w teście będzie zmieniane hasło
    public void resetPassword_SUCCESS() {
        PasswordResetDto passwordResetDto = new PasswordResetDto(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcmFkaXVrIiwiaXNzIjoiY3J1aXNlaHViIiwiZXhwIjoxNjE5ODU4NDA3LCJ2ZXJzaW9uIjo1LCJpYXQiOjE2MTk4NTcyMDcsImp0aSI6Ijk0M2EyMzM1LTFlZTctNGE4ZS1iNmFhLWU3Y2FiM2I3OWNmYSJ9.wI1xu7qZDCVrV-HYGCxgqbn9HVIr8mFW0JC0g_qZn3A",
                "aradiuk", "password");
        given().baseUri(accountBaseUri).contentType(MediaType.APPLICATION_JSON).body(passwordResetDto).post("/reset-password").then().statusCode(200);
    }

    @Test
    public void registerClientTest_SUCCESS() {
        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        given().baseUri(accountBaseUri).contentType(MediaType.APPLICATION_JSON).body(client).when().post("/client/registration").then().statusCode(204);
        // todo implement remove method to clean created data
    }

    @Test
    public void registerBusinessWorkerTest_SUCCESS() {
        BusinessWorkerForRegistrationDto businessWorkerDto = new BusinessWorkerForRegistrationDto("Artur", "Radiuk", randomAlphanumeric(15), randomAlphanumeric(10) + "@gmail.com",
                "abcABC123*", LanguageType.ENG, "123456789", "FirmaJez");
        given().baseUri(authBaseUri).contentType(MediaType.APPLICATION_JSON).body(businessWorkerDto).when().post("/business-worker/registration").then().statusCode(204);
    }

    @Test
    public void blockUserTest() throws JsonProcessingException {
        String adminToken = this.getAuthToken("rbranson", "abcABC123*");

        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        AccountDto account = registerClientAndGetAccountDto(client);

        Response response = given().header("Content-Type", "application/json").header(new Header("Authorization", "Bearer " + adminToken))
                .baseUri(accountBaseUri).get("/details/" + account.getLogin());

        AccountDetailsViewDto accountDetailsViewDto = objectMapper.readValue(response.asString(), AccountDetailsViewDto.class);
        assertTrue(accountDetailsViewDto.isActive());

        BlockAccountDto blockAccountDto = new BlockAccountDto(accountDetailsViewDto.getLogin(), accountDetailsViewDto.getVersion());
        given().contentType(ContentType.JSON).header("If-Match", accountDetailsViewDto.getEtag()).header(new Header("Authorization", "Bearer " + adminToken))
                .baseUri(accountBaseUri).body(blockAccountDto).put("/block").then().statusCode(HttpStatus.SC_OK);

        response = given().header("Content-Type", "application/json").header(new Header("Authorization", "Bearer " + adminToken))
                .baseUri(accountBaseUri).get("/details/" + account.getLogin());

        accountDetailsViewDto = objectMapper.readValue(response.asString(), AccountDetailsViewDto.class);
        assertFalse(accountDetailsViewDto.isActive());
    }

    @Test
    void grantAccessLevelTest_SUCCESS() throws JsonProcessingException, ETagException {
        String adminToken = this.getAuthToken("rbranson", "abcABC123*");

        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        AccountDto account = registerClientAndGetAccountDto(client);
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(account);

        Set<AccessLevelType> originalAccessLevels = account.getAccessLevels();
        assertThat(originalAccessLevels).doesNotContain(AccessLevelType.MODERATOR);

        GrantAccessLevelDto grantAccessLevel = new GrantAccessLevelDto(account.getLogin(), AccessLevelType.MODERATOR, account.getVersion());

        Response response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken)).body(grantAccessLevel).put("/grant-access-level");
        assertThat(response.getStatusCode()).isEqualTo(200);

        AccountDto updatedAccount = objectMapper.readValue(response.asString(), AccountDto.class);
        assertThat(updatedAccount.getAccessLevels()).contains(AccessLevelType.MODERATOR).containsAll(originalAccessLevels);
        // todo implement remove method to clean created data
    }

    @Test
    void changeAccessLevelState_SUCCESS() throws JsonProcessingException, ETagException {
        String adminToken = this.getAuthToken("rbranson", "abcABC123*");

        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        AccountDto account = registerClientAndGetAccountDto(client);

        grantAccessLevel(account, AccessLevelType.MODERATOR, account.getVersion());

        AccountDetailsViewDto accountDetails = getAccountDetailsViewDto(account.getLogin());

        Optional<AccessLevelDetailsViewDto> moderator = accountDetails.getAccessLevels().stream()
                .filter(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.MODERATOR).findFirst();

        assertThat(moderator).isPresent();
        boolean enabled = moderator.get().isEnabled();
        assertThat(enabled).isTrue();

        ChangeAccessLevelStateDto changeAccessLevelState = new ChangeAccessLevelStateDto(accountDetails.getLogin(),
                moderator.get().getAccessLevelType(), accountDetails.getVersion(), false);
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(changeAccessLevelState);

        Response response = getBaseUriETagRequest(etag)
                .header(new Header("Authorization", "Bearer " + adminToken))
                .contentType(ContentType.JSON)
                .body(changeAccessLevelState).put("/change-access-level-state");

        assertThat(response.getStatusCode()).isEqualTo(200);

        accountDetails = getAccountDetailsViewDto(account.getLogin());
        moderator = accountDetails.getAccessLevels().stream()
                .filter(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.MODERATOR).findFirst();

        assertThat(moderator).isPresent();
        boolean enabledAfterChanges = moderator.get().isEnabled();
        assertThat(enabledAfterChanges).isFalse();
    }

    @Test
    void changeAccessLevelState_FAIL() throws JsonProcessingException, ETagException {
        String adminToken = this.getAuthToken("rbranson", "abcABC123*");
        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        AccountDto account = registerClientAndGetAccountDto(client);

        grantAccessLevel(account, AccessLevelType.ADMINISTRATOR, account.getVersion());
        AccountDetailsViewDto accountDetails = getAccountDetailsViewDto(account.getLogin());

        Optional<AccessLevelDetailsViewDto> moderator = accountDetails.getAccessLevels().stream()
                .filter(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.ADMINISTRATOR).findFirst();

        assertThat(moderator).isPresent();
        boolean enabled = moderator.get().isEnabled();
        assertThat(enabled).isTrue();

        // Requesting change with invalid etag
        ChangeAccessLevelStateDto changeAccessLevelState = new ChangeAccessLevelStateDto(account.getLogin(),
                moderator.get().getAccessLevelType(), account.getVersion(), true);

        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(account);
        changeAccessLevelState.setAccountVersion(account.getVersion() - 1);

        Response response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken))
                .body(changeAccessLevelState).put("/change-access-level-state");

        assertThat(response.getStatusCode()).isEqualTo(406);
        assertThat(response.asString()).isEqualTo(ETAG_IDENTITY_INTEGRITY_ERROR);

        // Requesting enabling already enabled account
        changeAccessLevelState.setAccountVersion(account.getVersion() + 2);
        etag = EntityIdentitySignerVerifier.calculateEntitySignature(changeAccessLevelState);

        response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken))
                .body(changeAccessLevelState).put("/change-access-level-state");

        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.asString()).isEqualTo(ACCESS_LEVEL_ALREADY_ENABLED_ERROR);

        // Requesting disabling already disabled account
        changeAccessLevelState.setEnabled(false);
        getBaseUriETagRequest(etag).contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken))
                .body(changeAccessLevelState).put("/change-access-level-state");

        response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken))
                .body(changeAccessLevelState).put("/change-access-level-state");

        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.asString()).isEqualTo(ACCESS_LEVEL_ALREADY_DISABLED_ERROR);

        // Requesting changing state of not assigned access level

        Optional<AccessLevelDetailsViewDto> administrator = accountDetails.getAccessLevels().stream()
                .filter(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.BUSINESS_WORKER).findFirst();
        assertThat(administrator).isEmpty();

        changeAccessLevelState.setAccessLevel(AccessLevelType.BUSINESS_WORKER);
        response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken))
                .body(changeAccessLevelState).put("/change-access-level-state");

        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.getBody().asString()).isEqualTo(ACCESS_LEVEL_NOT_ASSIGNED_ERROR);
    }

    @Test
    void grantAccessLevelTest_FAIL() throws JsonProcessingException, ETagException {
        String adminToken = this.getAuthToken("rbranson", "abcABC123*");
        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        AccountDto account = registerClientAndGetAccountDto(client);
        GrantAccessLevelDto grantAccessLevel = new GrantAccessLevelDto(account.getLogin(), AccessLevelType.MODERATOR, account.getVersion());
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(account);

        // requesting granting accessLevel with no ETAG
        Response response = given().baseUri(accountBaseUri).contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken)).body(grantAccessLevel).put("/grant-access-level");
        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.asString()).isEqualTo(ETAG_EMPTY_ERROR);

        // requesting granting already assigned accessLevel
        response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken)).body(grantAccessLevel).put("/grant-access-level");
        assertThat(response.getStatusCode()).isEqualTo(200);


        grantAccessLevel.setAccountVersion(grantAccessLevel.getAccountVersion() + 2);
        response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken)).body(grantAccessLevel).put("/grant-access-level");
        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.asString()).isEqualTo(ACCESS_LEVEL_ALREADY_ASSIGNED_ERROR);

        // requesting granting not assignable accessLevel
        grantAccessLevel.setAccessLevel(AccessLevelType.BUSINESS_WORKER);

        response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken)).body(grantAccessLevel).put("/grant-access-level");
        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.asString()).isEqualTo(ACCESS_LEVEL_NOT_ASSIGNABLE_ERROR);

        // todo check for optimistic lock once working mechanism is implemented correctly
        // todo implement remove method to clean created data
    }

    @Test
    public void getAllAccountsTest_SUCCESS() throws JsonProcessingException {
        String adminToken = this.getAuthToken("rbranson", "abcABC123*");

        Response response = RestAssured.given().header("Content-Type", "application/json").header(new Header("Authorization", "Bearer " + adminToken)).baseUri(accountBaseUri).get("/accounts");
        String accountString = response.getBody().asString();
        List<AccountDtoForList> accountDtoList = Arrays.asList(objectMapper.readValue(accountString, AccountDtoForList[].class));

        int numberOfUsers = accountDtoList.size();
        ClientForRegistrationDto client = getSampleClientForRegistrationDto();

        assertTrue(accountDtoList.stream().noneMatch(account -> account.getLogin().equals(client.getLogin())));

        AccountDto account = registerClientAndGetAccountDto(client);

        response = RestAssured.given().contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken)).baseUri(accountBaseUri).get("/accounts");

        accountString = response.getBody().asString();
        accountDtoList = Arrays.asList(objectMapper.readValue(accountString, AccountDtoForList[].class));

        int numberOfUsersAfterChanges = accountDtoList.size();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertTrue(numberOfUsersAfterChanges > numberOfUsers);

        assertTrue(accountDtoList.stream().anyMatch(newAccount -> newAccount.getLogin().equals(account.getLogin())));

    }

    @Test
    public void unblockUserTest_SUCCESS() throws JsonProcessingException, ETagException { // todo fix this test
        String adminToken = this.getAuthToken("rbranson", "abcABC123*");

        AccountDto account = registerClientAndGetAccountDto(getSampleClientForRegistrationDto());
        BlockAccountDto blockAccountDto = new BlockAccountDto(account.getLogin(), account.getVersion());
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(blockAccountDto);
        given().contentType(ContentType.JSON).header("If-Match", etag).header(new Header("Authorization", "Bearer " + adminToken))
                .baseUri(accountBaseUri).body(blockAccountDto).put("/block");

        Response response = given().header("Content-Type", "application/json").header(new Header("Authorization", "Bearer " + adminToken))
                .baseUri(accountBaseUri).get("/details/" + account.getLogin());
        AccountDetailsViewDto accountDetailsViewDto = objectMapper.readValue(response.asString(), AccountDetailsViewDto.class);
        assertFalse(accountDetailsViewDto.isActive());

        UnblockAccountDto unblockAccountDto = new UnblockAccountDto(accountDetailsViewDto.getLogin(), accountDetailsViewDto.getVersion());
        given().contentType(ContentType.JSON).header("If-Match", accountDetailsViewDto.getEtag()).header(new Header("Authorization", "Bearer " + adminToken))
                .baseUri(accountBaseUri).body(unblockAccountDto).put("/unblock").then().statusCode(HttpStatus.SC_OK);

        response = given().header("Content-Type", "application/json").header(new Header("Authorization", "Bearer " + adminToken))
                .baseUri(accountBaseUri).get("/details/" + account.getLogin());

        accountDetailsViewDto = objectMapper.readValue(response.asString(), AccountDetailsViewDto.class);
        assertTrue(accountDetailsViewDto.isActive());
    }


    private ClientForRegistrationDto getSampleClientForRegistrationDto() {
        AddressDto address = new AddressDto(1L, "Bortnyka", "30-302", "Pluzhne", "Ukraine");
        return new ClientForRegistrationDto("Artur", "Radiuk", randomAlphanumeric(15), randomAlphanumeric(10) + "@gmail.com",
                "abcABC123*", LanguageType.PL, address, "123456789");
    }

    private AccountDto registerClientAndGetAccountDto(ClientForRegistrationDto client) throws JsonProcessingException {
        given().baseUri(accountBaseUri).contentType("application/json").body(client).post("/client/registration");
        return getAccountDto(client.getLogin());
    }

    @Test
    public void changeEmailTest_SUCCESS() throws JsonProcessingException {
        String adminToken = this.getAuthToken("rbranson", "abcABC123*");
        AccountDto account = registerClientAndGetAccountDto(getSampleClientForRegistrationDto());
        Response res = given().baseUri(accountBaseUri).header(new Header("Authorization", "Bearer " + adminToken)).get("/" + account.getLogin());
        String etag = res.getHeader("Etag");

        AccountChangeEmailDto accountChangeEmailDto = new AccountChangeEmailDto(
                account.getLogin(),
                account.getVersion(),
                randomAlphanumeric(10) + "@gmail.com");

        given().baseUri(accountBaseUri).header("If-Match", etag)
                .contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken))
                .body(accountChangeEmailDto)
                .when()
                .put("/change-email").then().statusCode(204);


        Response changedRes = given().baseUri(accountBaseUri).header(new Header("Authorization", "Bearer " + adminToken)).get("/" + account.getLogin());
        AccountDto changedAccount = objectMapper.readValue(changedRes.thenReturn().asString(), AccountDto.class);

        Assertions.assertEquals(accountChangeEmailDto.getNewEmail(), changedAccount.getEmail());
    }

    @Test
    public void changeEmailTest_FAIL() throws JsonProcessingException, BaseAppException {
        String adminToken = this.getAuthToken("rbranson", "abcABC123*");

        AccountDto account = registerClientAndGetAccountDto(getSampleClientForRegistrationDto());
        Response res = given().baseUri(accountBaseUri).header(new Header("Authorization", "Bearer " + adminToken)).get("/" + account.getLogin());

        AccountChangeEmailDto accountChangeEmailDto = new AccountChangeEmailDto(
                account.getLogin(),
                account.getVersion() - 1,
                randomAlphanumeric(10) + "@gmail.com");
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(accountChangeEmailDto);

        Response response = given().baseUri(accountBaseUri).header("If-Match", etag)
                .contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken))
                .body(accountChangeEmailDto)
                .when()
                .put("/change-email");

        assertEquals(409, response.getStatusCode());
        assertEquals(OPTIMISTIC_LOCK_EXCEPTION, response.asString());

        Response notChangedRes = given().baseUri(accountBaseUri).header(new Header("Authorization", "Bearer " + adminToken)).get("/" + account.getLogin());
        AccountDto notChangedAccount = objectMapper.readValue(notChangedRes.thenReturn().asString(), AccountDto.class);
        Assertions.assertEquals(account.getEmail(), notChangedAccount.getEmail());
    }

    private AccountDto getAccountDto(String login) throws JsonProcessingException {
        String authToken = this.getAuthToken("rbranson", "abcABC123*");
        return objectMapper.readValue(given().baseUri(accountBaseUri).header(new Header("Authorization", "Bearer " + authToken)).get("/" + login).thenReturn().asString(), AccountDto.class);
    }


    private AccountDetailsViewDto getAccountDetailsViewDto(String login) throws JsonProcessingException {
        String authToken = this.getAuthToken("rbranson", "abcABC123*");
        String responseString = given().baseUri(accountBaseUri).header(new Header("Authorization", "Bearer " + authToken)).get("/details/" + login)
                .thenReturn().asString();
        return objectMapper.readValue(responseString, AccountDetailsViewDto.class);
    }

    private void grantAccessLevel(AccountDto account, AccessLevelType accessLevelType, long accountVersion) throws ETagException {
        String authToken = this.getAuthToken("rbranson", "abcABC123*");
        if (!account.getAccessLevels().contains(accessLevelType)) {
            GrantAccessLevelDto grantAccessLevel = new GrantAccessLevelDto(account.getLogin(), accessLevelType, accountVersion);
            String etag = EntityIdentitySignerVerifier.calculateEntitySignature(account);

            getBaseUriETagRequest(etag).contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + authToken)).body(grantAccessLevel).put("/grant-access-level");
        }
    }

    private RequestSpecification getBaseUriETagRequest(String etag) {
        return given().baseUri(accountBaseUri).header("If-Match", etag);
    }

    @Test
    public void changeClientDataTest_SUCCESS() throws JsonProcessingException, ETagException {
        String adminToken = this.getAuthToken("rbranson", "abcABC123*");

        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        AccountDto account = registerClientAndGetAccountDto(client);
        AccountDetailsViewDto dto = getAccountDetailsViewDto(account.getLogin());
        long version = dto.getAccessLevels().stream()
                .filter(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.CLIENT).findFirst().get().getAccVersion();

        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(account);
        OtherAddressChangeDto newAddress = new OtherAddressChangeDto(100L, "Aleja Zmieniona", "94-690", "Lodz", "Polska");
        OtherClientChangeDataDto otherClientChangeDataDto = new OtherClientChangeDataDto(account.getLogin(), account.getVersion(),

                "888888888",
                newAddress,
                version);
        Response response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken)).body(otherClientChangeDataDto).put("/change-client-data");
        assertThat(response.getStatusCode()).isEqualTo(200);
        OtherClientChangeDataDto updatedAccount = objectMapper.readValue(response.asString(), OtherClientChangeDataDto.class);
        assertThat(updatedAccount.getNewAddress().getNewStreet()).isEqualTo("Aleja Zmieniona");
        assertThat(updatedAccount.getNewAddress().getNewPostalCode()).isEqualTo("94-690");
        assertThat(updatedAccount.getNewAddress().getNewCity()).isEqualTo("Lodz");
        assertThat(updatedAccount.getNewAddress().getNewCountry()).isEqualTo("Polska");
        assertThat(updatedAccount.getNewPhoneNumber()).isEqualTo("888888888");


    }

    @Test
    public void changeBusinessWorkerDataTest_SUCCESS() throws JsonProcessingException, ETagException {
        String adminToken = this.getAuthToken("rbranson", "abcABC123*");

        BusinessWorkerForRegistrationDto worker = getSampleBusinessWorkerForRegistrationDto();
        AccountDto account = registerBusinessWorkerAndGetAccountDto(worker);
        AccountDetailsViewDto dto = getAccountDetailsViewDto(account.getLogin());
        long version = dto.getAccessLevels().stream()
                .filter(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.BUSINESS_WORKER).findFirst().get().getAccVersion();

        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(account);
        OtherBusinessWorkerChangeDataDto otherBusinessWorkerChangeDataDto = new OtherBusinessWorkerChangeDataDto(account.getLogin(), account.getVersion(),
                "888888888", version);
        Response response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken)).body(otherBusinessWorkerChangeDataDto).put("/change-business-worker-data");
        assertThat(response.getStatusCode()).isEqualTo(200);
        OtherBusinessWorkerChangeDataDto updatedAccount = objectMapper.readValue(response.asString(), OtherBusinessWorkerChangeDataDto.class);

        assertThat(updatedAccount.getNewPhoneNumber()).isEqualTo("888888888");

    }

    @Test
    public void changeModeratorOrAdministratorDataTest_SUCCESS() throws JsonProcessingException, ETagException {
        String adminToken = this.getAuthToken("rbranson", "abcABC123*");

        AccountDto account = getAccountDto("rbranson");
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(account);
        OtherAccountChangeDataDto otherAccountChangeDataDto = new OtherAccountChangeDataDto(account.getLogin(), account.getVersion(),
                "Damian",
                "Bednarek",
                randomAlphanumeric(10) + "@gmail.com");
        Response response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + adminToken)).body(otherAccountChangeDataDto).put("/change-account-data");
        assertThat(response.getStatusCode()).isEqualTo(200);
        AccountDto updatedAccount = objectMapper.readValue(response.asString(), AccountDto.class);
        assertThat(updatedAccount.getFirstName()).isEqualTo("Damian");
        assertThat(updatedAccount.getSecondName()).isEqualTo("Bednarek");
    }

    private BusinessWorkerForRegistrationDto getSampleBusinessWorkerForRegistrationDto() {
        return new BusinessWorkerForRegistrationDto("Artur", "Radiuk", randomAlphanumeric(15), randomAlphanumeric(10) + "@gmail.com",
                "abcABC123*", LanguageType.PL, "123456789", "FirmaJez");
    }

    private AccountDto registerBusinessWorkerAndGetAccountDto(BusinessWorkerForRegistrationDto worker) throws JsonProcessingException {
        given().baseUri(authBaseUri).contentType("application/json").body(worker).post("/business-worker/registration").then().statusCode(204);
        return getAccountDto(worker.getLogin());
    }

    private String getAuthToken(String login, String password) {
        AuthenticateDto authenticateDto = new AuthenticateDto(login, password);

        Response response = given().baseUri(authBaseUri)
                .contentType(ContentType.JSON)
                .body(authenticateDto)
                .when()
                .post("/sign-in");
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        return response.getBody().asString();
    }



}