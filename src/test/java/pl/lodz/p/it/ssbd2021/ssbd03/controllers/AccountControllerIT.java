package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.ChangeAccessLevelStateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDtoForList;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.GrantAccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccessLevelDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccountDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;

import javax.ws.rs.core.MediaType;
import java.util.*;


import java.util.Set;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;


class AccountControllerIT {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String baseUri;

    AccountControllerIT() {
        Properties securityProperties = PropertiesReader.getSecurityProperties();
        baseUri = securityProperties.getProperty("app.baseurl") + "/api/account";
    }

    @Test
    public void requestPasswordReset_SUCCESS() throws JsonProcessingException {
        AccountDto accountDto = registerClientAndGetAccountDto(getSampleClientForRegistrationDto());
        given().baseUri(baseUri).contentType(MediaType.APPLICATION_JSON).post("/request-password-reset/" + accountDto.getLogin()).then().statusCode(200);
    }
   @Test
    public void requestSomeonesPasswordReset_SUCCESS() throws JsonProcessingException {
        AccountDto accountDto = registerClientAndGetAccountDto(getSampleClientForRegistrationDto());
        given().baseUri(baseUri).contentType(MediaType.APPLICATION_JSON).post("/request-someones-password-reset/" + accountDto.getLogin() + "/" + accountDto.getEmail()).then().statusCode(200);
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
    public void blockUserTest() throws JsonProcessingException {

        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        AccountDto account = registerClientAndGetAccountDto(client);

        Response response = given().header("Content-Type", "application/json").baseUri(baseUri).get("/accounts");
        String accountString = response.getBody().asString();
        List<AccountDtoForList> accountDtoList = Arrays.asList(objectMapper.readValue(accountString, AccountDtoForList[].class));
        AccountDtoForList accountDtoForList = accountDtoList.stream().filter(acc -> acc.getLogin().equals(account.getLogin())).findFirst().get();
        BlockAccountDto blockAccountDto = new BlockAccountDto(accountDtoForList.getLogin(), accountDtoForList.getVersion());

        response = given().header("Content-Type", "application/json").baseUri(baseUri).get("/accounts");
        accountString = response.getBody().asString();
        accountDtoList = Arrays.asList(objectMapper.readValue(accountString, AccountDtoForList[].class));
        accountDtoForList = accountDtoList.stream().filter(acc -> acc.getLogin().equals(account.getLogin())).findFirst().get();

        Response postResponse = given().contentType(ContentType.JSON).header("If-Match", accountDtoForList.getEtag()).
                baseUri(baseUri).body(blockAccountDto).put("/block");

        response = given().header("Content-Type", "application/json").baseUri(baseUri).get("/accounts");
        accountString = response.getBody().asString();
        accountDtoList = Arrays.asList(objectMapper.readValue(accountString, AccountDtoForList[].class));
        accountDtoForList = accountDtoList.stream().filter(acc -> acc.getLogin().equals(account.getLogin())).findFirst().get();

        assertFalse(accountDtoForList.isActive());
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void grantAccessLevelTest_SUCCESS() throws JsonProcessingException {
        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        AccountDto account = registerClientAndGetAccountDto(client);
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(account);

        Set<AccessLevelType> originalAccessLevels = account.getAccessLevels();
        assertThat(originalAccessLevels).doesNotContain(AccessLevelType.MODERATOR);

        GrantAccessLevelDto grantAccessLevel = new GrantAccessLevelDto(account.getLogin(), AccessLevelType.MODERATOR, account.getVersion());

        Response response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).body(grantAccessLevel).put("/grant-access-level");
        assertThat(response.getStatusCode()).isEqualTo(200);

        AccountDto updatedAccount = objectMapper.readValue(response.asString(), AccountDto.class);
        assertThat(updatedAccount.getAccessLevels()).contains(AccessLevelType.MODERATOR).containsAll(originalAccessLevels);
        // todo implement remove method to clean created data
    }

    @Test
    void changeAccessLevelState_SUCCESS() throws JsonProcessingException {
        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        AccountDto account = registerClientAndGetAccountDto(client);

        grantAccessLevel(account, AccessLevelType.MODERATOR, account.getVersion());

        AccountDetailsViewDto accountDetails = getAccountDetailsViewDto(account.getLogin());

        Optional<AccessLevelDetailsViewDto> moderator = accountDetails.getAccessLevels().stream()
                .filter(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.MODERATOR).findFirst();

        assertThat(moderator).isPresent();
        boolean enabled = moderator.get().isEnabled();
        assertThat(enabled).isTrue();

        ChangeAccessLevelStateDto changeAccessLevelState = new ChangeAccessLevelStateDto(account.getLogin(),
                moderator.get().getAccessLevelType(), account.getVersion(), false);
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(changeAccessLevelState);

        Response response = getBaseUriETagRequest(etag).contentType(ContentType.JSON)
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
    void changeAccessLevelState_FAIL() throws JsonProcessingException {
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

        Response response = getBaseUriETagRequest(etag).contentType(ContentType.JSON)
                .body(changeAccessLevelState).put("/change-access-level-state");

        assertThat(response.getStatusCode()).isEqualTo(406);
        assertThat(response.asString()).isEqualTo(ETAG_IDENTITY_INTEGRITY_ERROR);

        // Requesting enabling already enabled account
        changeAccessLevelState = new ChangeAccessLevelStateDto(account.getLogin(),
                moderator.get().getAccessLevelType(), account.getVersion(), true);

        response = getBaseUriETagRequest(etag).contentType(ContentType.JSON)
                .body(changeAccessLevelState).put("/change-access-level-state");

        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.asString()).isEqualTo(ACCESS_LEVEL_ALREADY_ENABLED_ERROR);

        // Requesting disabling already disabled account
        changeAccessLevelState.setEnabled(false);
        getBaseUriETagRequest(etag).contentType(ContentType.JSON)
                .body(changeAccessLevelState).put("/change-access-level-state");

        response = getBaseUriETagRequest(etag).contentType(ContentType.JSON)
                .body(changeAccessLevelState).put("/change-access-level-state");

        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.asString()).isEqualTo(ACCESS_LEVEL_ALREADY_DISABLED_ERROR);

        // Requesting changing state of not assigned access level

        Optional<AccessLevelDetailsViewDto> administrator = accountDetails.getAccessLevels().stream()
                .filter(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.BUSINESS_WORKER).findFirst();
        assertThat(administrator).isEmpty();

        changeAccessLevelState.setAccessLevel(AccessLevelType.BUSINESS_WORKER);
        response = getBaseUriETagRequest(etag).contentType(ContentType.JSON)
                .body(changeAccessLevelState).put("/change-access-level-state");

        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.getBody().asString()).isEqualTo(ACCESS_LEVEL_NOT_ASSIGNED_ERROR);
    }

    @Test
    void grantAccessLevelTest_FAIL() throws JsonProcessingException {
        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        AccountDto account = registerClientAndGetAccountDto(client);
        GrantAccessLevelDto grantAccessLevel = new GrantAccessLevelDto(account.getLogin(), AccessLevelType.MODERATOR, account.getVersion());
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(account);

        // requesting granting accessLevel with no ETAG
        Response response = given().baseUri(baseUri).contentType(ContentType.JSON).body(grantAccessLevel).put("/grant-access-level");
        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.asString()).isEqualTo(ETAG_EMPTY_ERROR);

        // requesting granting already assigned accessLevel
        response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).body(grantAccessLevel).put("/grant-access-level");
        assertThat(response.getStatusCode()).isEqualTo(200);


        response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).body(grantAccessLevel).put("/grant-access-level");
        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.asString()).isEqualTo(ACCESS_LEVEL_ALREADY_ASSIGNED_ERROR);

        // requesting granting not assignable accessLevel
        grantAccessLevel.setAccessLevel(AccessLevelType.BUSINESS_WORKER);

        response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).body(grantAccessLevel).put("/grant-access-level");
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
    public void unblockUserTest_SUCCESS() throws JsonProcessingException {

        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        AccountDto account = registerClientAndGetAccountDto(client);

        Response response = given().header("Content-Type", "application/json").baseUri(baseUri).get("/accounts");
        String accountString = response.getBody().asString();
        List<AccountDtoForList> accountDtoList = Arrays.asList(objectMapper.readValue(accountString, AccountDtoForList[].class));
        AccountDtoForList accountDtoForList = accountDtoList.stream().filter(acc -> acc.getLogin().equals(account.getLogin())).findFirst().get();
        UnblockAccountDto unblockAccountDto = new UnblockAccountDto(accountDtoForList.getLogin(), accountDtoForList.getVersion());

        response = given().header("Content-Type", "application/json").baseUri(baseUri).get("/accounts");
        accountString = response.getBody().asString();
        accountDtoList = Arrays.asList(objectMapper.readValue(accountString, AccountDtoForList[].class));
        accountDtoForList = accountDtoList.stream().filter(acc -> acc.getLogin().equals(account.getLogin())).findFirst().get();
        assertFalse(accountDtoForList.isActive());

        Response postResponse = given().contentType(ContentType.JSON).header("If-Match", accountDtoForList.getEtag()).
                baseUri(baseUri).body(unblockAccountDto).put("/unblock");

        response = given().header("Content-Type", "application/json").baseUri(baseUri).get("/accounts");
        accountString = response.getBody().asString();
        accountDtoList = Arrays.asList(objectMapper.readValue(accountString, AccountDtoForList[].class));
        accountDtoForList = accountDtoList.stream().filter(acc -> acc.getLogin().equals(account.getLogin())).findFirst().get();

        assertTrue(accountDtoForList.isActive());
        assertEquals(200, response.getStatusCode());


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

    @Test
    public void changeEmailTest_SUCCESS() throws JsonProcessingException {

        AccountDto account = registerClientAndGetAccountDto(getSampleClientForRegistrationDto());
        Response res = given().baseUri(baseUri).get("/" + account.getLogin());
        String etag = res.getHeader("Etag");

        AccountChangeEmailDto accountChangeEmailDto = new AccountChangeEmailDto(
                account.getLogin(),
                account.getVersion(),
                randomAlphanumeric(10) + "@gmail.com");

        given().baseUri(baseUri).header("If-Match", etag)
                .contentType(ContentType.JSON)
                .body(accountChangeEmailDto)
                .when()
                .put("/change_email").then().statusCode(204);


        Response changedRes = given().baseUri(baseUri).get("/" + account.getLogin());
        AccountDto changedAccount = objectMapper.readValue(changedRes.thenReturn().asString(), AccountDto.class);

        Assertions.assertEquals(accountChangeEmailDto.getNewEmail(), changedAccount.getEmail());
    }

    @Test
    public void changeEmailTest_FAIL() throws JsonProcessingException {

        AccountDto account = registerClientAndGetAccountDto(getSampleClientForRegistrationDto());
        Response res = given().baseUri(baseUri).get("/" + account.getLogin());

        AccountChangeEmailDto accountChangeEmailDto = new AccountChangeEmailDto(
                account.getLogin(),
                account.getVersion() - 1,
                randomAlphanumeric(10) + "@gmail.com");
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(accountChangeEmailDto);

        given().baseUri(baseUri).header("If-Match", etag)
                .contentType(ContentType.JSON)
                .body(accountChangeEmailDto)
                .when()
                .put("/change_email").then().statusCode(406);

        Response notChangedRes = given().baseUri(baseUri).get("/" + account.getLogin());
        AccountDto notChangedAccount = objectMapper.readValue(notChangedRes.thenReturn().asString(), AccountDto.class);
        Assertions.assertEquals(account.getEmail(), notChangedAccount.getEmail());
    }

    private AccountDto getAccountDto(String login) throws JsonProcessingException {
        return objectMapper.readValue(given().baseUri(baseUri).get("/" + login).thenReturn().asString(), AccountDto.class);
    }


    private AccountDetailsViewDto getAccountDetailsViewDto(String login) throws JsonProcessingException {
        String responseString = given().baseUri(baseUri).get("/details-view/" + login)
                .thenReturn().asString();
        return objectMapper.readValue(responseString, AccountDetailsViewDto.class);
    }

    private void grantAccessLevel(AccountDto account, AccessLevelType accessLevelType, Long accountVersion) {
        if (!account.getAccessLevels().contains(accessLevelType)) {
            GrantAccessLevelDto grantAccessLevel = new GrantAccessLevelDto(account.getLogin(), accessLevelType, accountVersion);
            String etag = EntityIdentitySignerVerifier.calculateEntitySignature(account);

            getBaseUriETagRequest(etag).contentType(ContentType.JSON).body(grantAccessLevel).put("/grant-access-level");
        }
    }
    private RequestSpecification getBaseUriETagRequest(String etag) {
        return given().baseUri(baseUri).header("If-Match", etag);
    }

    @Test
    public void changeClientDataTest_SUCCESS() throws JsonProcessingException {
        ClientForRegistrationDto client = getSampleClientForRegistrationDto();
        AccountDto account = registerClientAndGetAccountDto(client);
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(account);
        OtherAddressChangeDto newAddress = new OtherAddressChangeDto(100L, "Aleja Zmieniona", "94-690", "Lodz", "Polska", "rbranson");
        OtherClientChangeDataDto otherClientChangeDataDto = new OtherClientChangeDataDto(account.getLogin(), account.getVersion(),
                "Damian",
                "Bednarek",
                "zmienony@gmail.com",
                "888888888",
                newAddress,
                "rbranson");
        Response response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).body(otherClientChangeDataDto).put("/changeOtherData/client");
        assertThat(response.getStatusCode()).isEqualTo(200);
        OtherClientChangeDataDto updatedAccount = objectMapper.readValue(response.asString(), OtherClientChangeDataDto.class);
        assertThat(updatedAccount.getNewFirstName()).isEqualTo("Damian");
        assertThat(updatedAccount.getNewSecondName()).isEqualTo("Bednarek");
        assertThat(updatedAccount.getNewAddress().getNewStreet()).isEqualTo("Aleja Zmieniona");
        assertThat(updatedAccount.getNewPhoneNumber()).isEqualTo("888888888");
        assertThat(updatedAccount.getAlteredBy()).isEqualTo("rbranson");

    }

    @Test
    public void changeBusinessWorkerDataTest_SUCCESS() throws JsonProcessingException {
        BusinessWorkerForRegistrationDto worker = getSampleBusinessWorkerForRegistrationDto();
        AccountDto account = registerBusinessWorkerAndGetAccountDto(worker);
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(account);
        OtherBusinessWorkerChangeDataDto otherBusinessWorkerChangeDataDto = new OtherBusinessWorkerChangeDataDto(account.getLogin(), account.getVersion(),
                "Damian",
                "Bednarek",
                "zmienony@gmail.com",
                "888888888",
                "rbranson");
        Response response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).body(otherBusinessWorkerChangeDataDto).put("/changeOtherData/businessworker");
        assertThat(response.getStatusCode()).isEqualTo(200);
        OtherBusinessWorkerChangeDataDto updatedAccount = objectMapper.readValue(response.asString(), OtherBusinessWorkerChangeDataDto.class);
        assertThat(updatedAccount.getNewFirstName()).isEqualTo("Damian");
        assertThat(updatedAccount.getNewSecondName()).isEqualTo("Bednarek");
        assertThat(updatedAccount.getNewPhoneNumber()).isEqualTo("888888888");
        assertThat(updatedAccount.getAlteredBy()).isEqualTo("rbranson");
    }

    @Test
    public void changeModeratorOrAdministratorDataTest_SUCCESS() throws JsonProcessingException {
        AccountDto account = getAccountDto("rbranson");
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(account);
        OtherAccountChangeDataDto otherAccountChangeDataDto = new OtherAccountChangeDataDto(account.getLogin(), account.getVersion(),
                "Damian",
                "Bednarek",
                "zmienony@gmail.com",
                "rbranson");
        Response response = getBaseUriETagRequest(etag).contentType(ContentType.JSON).body(otherAccountChangeDataDto).put("/changeOtherData");
        assertThat(response.getStatusCode()).isEqualTo(200);
        AccountDto updatedAccount = objectMapper.readValue(response.asString(), AccountDto.class);
        assertThat(updatedAccount.getFirstName()).isEqualTo("Damian");
        assertThat(updatedAccount.getSecondName()).isEqualTo("Bednarek");
    }

    private BusinessWorkerForRegistrationDto getSampleBusinessWorkerForRegistrationDto() {
        return new BusinessWorkerForRegistrationDto("Artur", "Radiuk", randomAlphanumeric(15), randomAlphanumeric(10) + "@gmail.com",
                "123456789", LanguageType.PL, "123456789", "FirmaJez");
    }
    private AccountDto registerBusinessWorkerAndGetAccountDto(BusinessWorkerForRegistrationDto worker) throws JsonProcessingException {
        given().baseUri(baseUri).contentType("application/json").body(worker).post("/business-worker/registration").then().statusCode(204);
        return getAccountDto(worker.getLogin());
    }




}