package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.BusinessWorkerDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.ClientDto;
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

    @Test
    public void registerClientTest_SUCCESS() {
        pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto address = new pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto(1L, "Bortnyka", "30-302", "Pluzhne", "Ukraine");
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

    private String registerSampleClientAndGetLogin() {
        AddressDto address = new AddressDto(1L, "Bortnyka", "30-302", "Pluzhne", "Ukraine");
        ClientForRegistrationDto client = new ClientForRegistrationDto("Klient", "Kowalski", randomAlphanumeric(15), randomAlphanumeric(10) + "@gmail.com",
                "123456789", LanguageType.PL, address, "123456789");
        given().baseUri(baseUri).contentType("application/json").body(client).when().post("account/client/registration");
        return client.getLogin();
    }

    private String registerSampleBusinessWorkerAndGetLogin() {
        BusinessWorkerForRegistrationDto bs = new BusinessWorkerForRegistrationDto("Pracownik", "Kowalski", randomAlphanumeric(15), randomAlphanumeric(15), "1234567890", LanguageType.PL, "123456789", "BardzoBeautyCompany");

        given().baseUri(baseUri).contentType("application/json").body(bs).when().post("account/businessworker/registration");
        return bs.getLogin();
    }

    private String registerSampleModeratorAndGetLogin() {
        ModeratorForRegistrationDto moderator = new ModeratorForRegistrationDto("Moderator", "Kowalski", randomAlphanumeric(15), randomAlphanumeric(15), "0987654321", LanguageType.ENG);

        given().baseUri(baseUri).contentType("application/json").body(moderator).when().post("account/moderator/registration");

        return moderator.getLogin();
    }

    private String registerSampleAdministratorAndGetLogin() {
        AdministratorForRegistrationDto administrator = new AdministratorForRegistrationDto("Admin", "Kowalski", randomAlphanumeric(15), randomAlphanumeric(15), "0987654321", LanguageType.ENG);

        given().baseUri(baseUri).contentType("application/json").body(administrator).when().post("account/administrator/registration");

        return administrator.getLogin();
    }

    @Test
    public void changeClientDataTest_SUCCESS() throws JsonProcessingException {
        String login = registerSampleClientAndGetLogin();
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
        System.out.println(login);
        Response res = given().baseUri(baseUri).get("account/" + login);

        res.prettyPrint();

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
    }

    @Test
    public void changeModeratorDataTest_FAIL() throws JsonProcessingException {
        Response res = given().baseUri(baseUri).get("account/mzuckerberg");

        AccountDto account = objectMapper.readValue(res.thenReturn().asString(), AccountDto.class);

        String etag = res.header("Etag");

        ModeratorChangeDataDto moderatorChangeDataDto = new ModeratorChangeDataDto(
                account.getLogin(),
                account.getVersion() - 1,
                "Zly Marcus",
                "Zly Sugarus");

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
        Response res = given().baseUri(baseUri).get("account/rbranson");

        AccountDto account = objectMapper.readValue(res.thenReturn().asString(), AccountDto.class);

        String etag = res.header("Etag");

        AdministratorChangeDataDto administratorChangeDataDto = new AdministratorChangeDataDto(
                account.getLogin(),
                account.getVersion(),
                "Rubertiono",
                "Bransolino");

        given()
                .baseUri(baseUri)
                .header("If-Match", etag)
                .contentType("application/json")
                .body(administratorChangeDataDto)
                .when()
                .put("account/administrator/changedata")
                .then()
                .statusCode(204);
    }

    @Test
    public void changeAdministratorDataTest_FAIL() throws JsonProcessingException {
        Response res = given().baseUri(baseUri).get("account/rbranson");

        AccountDto account = objectMapper.readValue(res.thenReturn().asString(), AccountDto.class);

        String etag = res.header("Etag");

        AdministratorChangeDataDto administratorChangeDataDto = new AdministratorChangeDataDto(
                account.getLogin(),
                account.getVersion(),
                "Zly Rubertiono",
                "Zly Bransolino");

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