package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.AccountChangeEmailDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.AdministratorForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ModeratorForRegistrationDto;

import static io.restassured.RestAssured.given;


class AccountControllerTest {

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

    @Test
    public void changeEmailTest() {
        AccountChangeEmailDto accountChangeEmailDto = new AccountChangeEmailDto("rbranson", 0L, "zmieniony@gmail.com");

        Response res = given().baseUri(baseUri).contentType("application/json").body(accountChangeEmailDto).when().put("account/change_email");
        res.then().statusCode(204);
    }

    @Test
    public void changeClientDataTest_SUCCESS() {
        AddressChangeDto newAddress = new AddressChangeDto(100L, "Aleja Zmieniona", "94-690", "Lodz", "Polska");
        ClientChangeDataDto clientChangeDataDto = new ClientChangeDataDto("emusk",0L, "Elonczek", "Maseczek", "987654321", newAddress);

        given().baseUri(baseUri).contentType("application/json").body(clientChangeDataDto).when().put("account/client/changedata").then().statusCode(204);

    }

    @Test
    public void changeBusinessWorkerDataTest_SUCCESS() {
        BusinessWorkerChangeDataDto businessWorkerChangeDataDto = new BusinessWorkerChangeDataDto("jbezos", 0L, "Jefus", "Besoses", "000000001");

        given().baseUri(baseUri).contentType("application/json").body(businessWorkerChangeDataDto).when().put("account/businessworker/changedata").then().statusCode(204);
    }

    @Test
    public void changeModeratorDataTest_SUCCESS() {
        ModeratorChangeDataDto moderatorChangeDataDto = new ModeratorChangeDataDto("mzuckerberg", 0L, "Marcus", "Sugarus");

        given().baseUri(baseUri).contentType("application/json").body(moderatorChangeDataDto).when().put("account/moderator/changedata").then().statusCode(204);
    }

    @Test
    public void changeAdministratorDataTest_SUCCESS() {
        AdministratorChangeDataDto administratorChangeDataDto = new AdministratorChangeDataDto("rbranson", 0L, "Rubertiono", "Bransolino");

        given().baseUri(baseUri).contentType("application/json").body(administratorChangeDataDto).when().put("account/administrator/changedata").then().statusCode(204);
    }
}