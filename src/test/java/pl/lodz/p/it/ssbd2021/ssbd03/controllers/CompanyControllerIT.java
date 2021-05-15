package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AuthenticateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

class CompanyControllerIT {
    private final String baseUri;
    private final String authBaseUri;

    public CompanyControllerIT() {
        Properties securityProperties = PropertiesReader.getSecurityProperties();
        baseUri = securityProperties.getProperty("app.baseurl") + "/api";
        authBaseUri = securityProperties.getProperty("app.baseurl") + "/api/signin";

    }


    @Test
    void getAllCompaniesInfo_SUCCESS() {
        String adminToken = getAuthToken("rbranson","abcABC123*");
        List<CompanyLightDto> companies = Arrays.asList(new CompanyLightDto("FirmaJez", 1265485965L),
                new CompanyLightDto("GroveStreetFamilly", 2354685748L),
                new CompanyLightDto("BeautifulCompany", 9568545875L));
        given().baseUri(baseUri).when().header(new Header("Authorization", "Bearer " + adminToken))
                .get("company/companiesinfo").then().statusCode(200).and().body(containsString(new Gson().toJson(companies)));
    }

    private String getAuthToken(String login, String password) {
        AuthenticateDto authenticateDto = new AuthenticateDto(login, password);

        Response response = given().baseUri(authBaseUri)
                .contentType(ContentType.JSON)
                .body(authenticateDto)
                .when()
                .post("/auth");
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        return response.getBody().asString();
    }
}