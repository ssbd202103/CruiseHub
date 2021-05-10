package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.AdministratorForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ModeratorForRegistrationDto;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

class AccountControllerTest {

    private static final String baseUri = "http://localhost:8080/cruisehub/api/";
    private final ObjectMapper objectMapper = new ObjectMapper();

    AccountControllerTest() {
    }





    private String registerSampleClientAndGetLogin() {
        AddressDto address = new AddressDto(1L, "Bortnyka", "30-302", "Pluzhne", "Ukraine");
        ClientForRegistrationDto client = new ClientForRegistrationDto("Klient", "Kowalski", randomAlphanumeric(15), randomAlphanumeric(10) + "@gmail.com",
                "123456789", LanguageType.PL, address, "123456789");
        given().baseUri(baseUri).contentType("application/json").body(client).when().post("account/client/registration");
        return client.getLogin();
    }

    private String registerSampleBusinessWorkerAndGetLogin() {
        BusinessWorkerForRegistrationDto bs = new BusinessWorkerForRegistrationDto("Pracownikk", "Kowalskik",randomAlphanumeric(15),randomAlphanumeric(10)+"@gmail.com", "12345678",LanguageType.PL, "123654789","BeautifulCompany" );

        given().baseUri(baseUri).contentType("application/json").body(bs).when().post("account/businessworker/registration");
        return bs.getLogin();
    }

    private String registerSampleModeratorAndGetLogin() {
        ModeratorForRegistrationDto moderator = new ModeratorForRegistrationDto("Moderator", "Kowalski", randomAlphanumeric(15), randomAlphanumeric(10)+"@gmail.com", "0987654321", LanguageType.ENG);

        given().baseUri(baseUri).contentType("application/json").body(moderator).when().post("account/moderator/registration");

        return moderator.getLogin();
    }

    private String registerSampleAdministratorAndGetLogin() {
        AdministratorForRegistrationDto administrator = new AdministratorForRegistrationDto("Admin", "Kowalski", randomAlphanumeric(15),randomAlphanumeric(10)+"@gmail.com", "0987654321", LanguageType.ENG);

        given().baseUri(baseUri).contentType("application/json").body(administrator).when().post("account/administrator/registration");

        return administrator.getLogin();
    }

    @Test
    public void changeClientDataTest_SUCCESS() throws JsonProcessingException {
        String login = registerSampleClientAndGetLogin();

        Response res = given().baseUri(baseUri).get("account/" + login);

        AccountDto account = objectMapper.readValue(res.thenReturn().asString(), AccountDto.class);

        String etag = res.header("Etag");

        AddressDto newAddress = new AddressDto(100L, "Aleja Zmieniona", "94-690", "Lodz", "Polska");
        ClientChangeDataDto clientChangeDataDto = new ClientChangeDataDto(
                account.getLogin(),
                account.getVersion(),
                "Cdimię",
                "Cdnazwisko",
                "101010101",
                newAddress);

        Response r = given().baseUri(baseUri).header("If-Match", etag)
                .contentType(ContentType.JSON)
                .body(clientChangeDataDto)
                .when()
                .put("account/client/changedata");

        Response changedRes = given().baseUri(baseUri).get("account/client/" + login);

        ClientDto changedClient = objectMapper.readValue(changedRes.thenReturn().asString(), ClientDto.class);

        Assertions.assertEquals(clientChangeDataDto.getNewFirstName(), changedClient.getFirstName());
        Assertions.assertEquals(clientChangeDataDto.getNewSecondName(), changedClient.getSecondName());
        Assertions.assertEquals(clientChangeDataDto.getNewPhoneNumber(), changedClient.getPhoneNumber());
        Assertions.assertEquals(clientChangeDataDto.getNewAddress(), changedClient.getAddress());
    }

    @Test
    public void changeClientDataTest_FAIL() throws JsonProcessingException {
        String login = registerSampleClientAndGetLogin();

        Response res = given().baseUri(baseUri).get("account/" + login);

        AccountDto account = objectMapper.readValue(res.thenReturn().asString(), AccountDto.class);

        String etag = res.header("Etag");

        AddressDto newAddress = new AddressDto(100L, "Aleja Zmieniona", "94-690", "Lodz", "Polska");
        ClientChangeDataDto clientChangeDataDto = new ClientChangeDataDto(
                account.getLogin(),
                account.getVersion() - 1,
                "Cdimię",
                "Cdnazwisko",
                "101010101",
                newAddress);

        Response r = given().baseUri(baseUri).header("If-Match", etag)
                .contentType(ContentType.JSON)
                .body(clientChangeDataDto)
                .when()
                .put("account/client/changedata");

        r.then().statusCode(406);
    }

    @Test
    public void changeBusinessWorkerDataTest_SUCCESS() throws JsonProcessingException {
        String login = registerSampleBusinessWorkerAndGetLogin();

        Response res = given().baseUri(baseUri).get("account/" + login);

        AccountDto account = objectMapper.readValue(res.thenReturn().asString(), AccountDto.class);

        String etag = res.header("Etag");

        BusinessWorkerChangeDataDto businessWorkerChangeDataDto = new BusinessWorkerChangeDataDto(
                account.getLogin(),
                account.getVersion(),
                "Cdimię",
                "Cdnazwisko",
                "101010101");

        Response r = given().baseUri(baseUri).header("If-Match", etag)
                .contentType(ContentType.JSON)
                .body(businessWorkerChangeDataDto)
                .when()
                .put("account/businessworker/changedata");

        r.prettyPrint();
        r.then().statusCode(204);

        Response resOfChangeClient = given().baseUri(baseUri).get("account/businessworker/" + login);

        BusinessWorkerDto changedWorker = objectMapper.readValue(resOfChangeClient.thenReturn().asString(), BusinessWorkerDto.class);

        Assertions.assertEquals(businessWorkerChangeDataDto.getNewFirstName(), changedWorker.getFirstName());
        Assertions.assertEquals(businessWorkerChangeDataDto.getNewSecondName(), changedWorker.getSecondName());
        Assertions.assertEquals(businessWorkerChangeDataDto.getNewPhoneNumber(), changedWorker.getPhoneNumber());
    }

    @Test
    public void changeBusinessWorkerDataTest_FAIL() throws JsonProcessingException {
        Response res = given().baseUri(baseUri).get("account/jbezos");

        AccountDto account = objectMapper.readValue(res.thenReturn().asString(), AccountDto.class);

        String etag = res.header("Etag");

        BusinessWorkerChangeDataDto businessWorkerChangeDataDto = new BusinessWorkerChangeDataDto(
                account.getLogin(),
                account.getVersion() - 1,
                "Zly Jefus",
                "Zly Besoses",
                "100000000");

        given()
                .baseUri(baseUri)
                .header("If-Match", etag)
                .contentType("application/json")
                .body(businessWorkerChangeDataDto)
                .when()
                .put("account/businessworker/changedata")
                .then()
                .statusCode(406);
    }

    @Test
    public void changeModeratorDataTest_SUCCESS() throws JsonProcessingException {
        String login = registerSampleModeratorAndGetLogin();

        Response res = given().baseUri(baseUri).get("account/" + login);

        AccountDto account = objectMapper.readValue(res.thenReturn().asString(), AccountDto.class);

        String etag = res.header("Etag");

        ModeratorChangeDataDto moderatorChangeDataDto = new ModeratorChangeDataDto(
                account.getLogin(),
                account.getVersion(),
                "Marcus",
                "Sugarus");

        given()
                .baseUri(baseUri)
                .header("If-Match", etag)
                .contentType("application/json")
                .body(moderatorChangeDataDto)
                .when()
                .put("account/moderator/changedata")
                .then()
                .statusCode(204);

        Response changedRes = given().baseUri(baseUri).get("account/moderator/" + login);

        ModeratorDto changedModerator = objectMapper.readValue(changedRes.thenReturn().asString(), ModeratorDto.class);

        Assertions.assertEquals(moderatorChangeDataDto.getNewFirstName(), changedModerator.getFirstName());
        Assertions.assertEquals(moderatorChangeDataDto.getNewSecondName(), changedModerator.getSecondName());
    }

    @Test
    public void changeModeratorDataTest_FAIL() throws JsonProcessingException {
        String login = registerSampleModeratorAndGetLogin();

        Response res = given().baseUri(baseUri).get("account/" + login);

        AccountDto account = objectMapper.readValue(res.thenReturn().asString(), AccountDto.class);

        String etag = res.header("Etag");

        ModeratorChangeDataDto moderatorChangeDataDto = new ModeratorChangeDataDto(
                account.getLogin(),
                account.getVersion() - 1,
                "Marcus",
                "Sugarus");

        given()
                .baseUri(baseUri)
                .header("If-Match", etag)
                .contentType("application/json")
                .body(moderatorChangeDataDto)
                .when()
                .put("account/moderator/changedata")
                .then()
                .statusCode(406);
    }

    @Test
    public void changeAdministratorDataTest_SUCCESS() throws JsonProcessingException {
        String login = registerSampleAdministratorAndGetLogin();

        Response res = given().baseUri(baseUri).get("account/" + login);

        AccountDto account = objectMapper.readValue(res.thenReturn().asString(), AccountDto.class);

        String etag = res.header("Etag");

        AdministratorChangeDataDto administratorChangeDataDto = new AdministratorChangeDataDto(
                account.getLogin(),
                account.getVersion(),
                "Adminus",
                "Adminianus");

        given()
                .baseUri(baseUri)
                .header("If-Match", etag)
                .contentType("application/json")
                .body(administratorChangeDataDto)
                .when()
                .put("account/administrator/changedata")
                .then()
                .statusCode(204);

        Response changedRes = given().baseUri(baseUri).get("account/administrator/" + login);

        AdministratorDto changedAdministrator = objectMapper.readValue(changedRes.thenReturn().asString(), AdministratorDto.class);

        Assertions.assertEquals(administratorChangeDataDto.getNewFirstName(), changedAdministrator.getFirstName());
        Assertions.assertEquals(administratorChangeDataDto.getNewSecondName(), changedAdministrator.getSecondName());
    }

    @Test
    public void changeAdministratorDataTest_FAIL() throws JsonProcessingException {
        String login = registerSampleAdministratorAndGetLogin();

        Response res = given().baseUri(baseUri).get("account/" + login);

        AccountDto account = objectMapper.readValue(res.thenReturn().asString(), AccountDto.class);

        String etag = res.header("Etag");

        AdministratorChangeDataDto administratorChangeDataDto = new AdministratorChangeDataDto(
                account.getLogin(),
                account.getVersion() - 1,
                "Adminus",
                "Adminianus");

        given()
                .baseUri(baseUri)
                .header("If-Match", etag)
                .contentType("application/json")
                .body(administratorChangeDataDto)
                .when()
                .put("account/administrator/changedata")
                .then()
                .statusCode(406);
    }
}