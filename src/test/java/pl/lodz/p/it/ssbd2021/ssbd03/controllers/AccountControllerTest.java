package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.google.gson.Gson;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountChangeEmailDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.AdministratorForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ModeratorForRegistrationDto;
import static org.hamcrest.Matchers.containsString;

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
        AccountChangeEmailDto accountChangeEmailDto = new AccountChangeEmailDto("rbranson", 0L,"zmieniony@gmail.com");

        Response res = given().baseUri(baseUri).contentType("application/json").body(accountChangeEmailDto).when().put("account/change_email");
        res.then().statusCode(204);
    }
}