package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import groovyjarjarasm.asm.TypeReference;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;



import java.io.DataInput;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


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

       assertEquals(response.getStatusCode(),javax.ws.rs.core.Response.Status.OK.getStatusCode());
       assertEquals(accountDtoList.get(0).getEmail(),"rbranson@gmail.com");
       assertEquals(accountDtoList.get(1).getEmail(),"emusk@gmail.com");
       assertEquals(accountDtoList.get(2).getEmail(),"jbezos@gmail.com");
       assertEquals(accountDtoList.get(0).getFirstName(),"Richard");
       assertEquals(accountDtoList.get(0).getSecondName(),"Branson");


    }

}