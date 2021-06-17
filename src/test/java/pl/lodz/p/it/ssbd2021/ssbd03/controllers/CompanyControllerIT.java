package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.google.gson.Gson;
import io.restassured.http.Header;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.security.JWTHandler;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyControllerIT {
    private final String baseUri;
    private final String authBaseUri;

    public CompanyControllerIT() {
        Properties securityProperties = PropertiesReader.getSecurityProperties();
        baseUri = securityProperties.getProperty("app.baseurl") + "/api";
        authBaseUri = securityProperties.getProperty("app.baseurl") + "/api/auth";

    }


    @Test
    void getAllCompaniesInfo_SUCCESS() {
        String adminToken = getAuthToken("rbranson","abcABC123*");
        List<CompanyLightDto> companies = Arrays.asList(new CompanyLightDto("FirmaJez", 1265485965L),
                new CompanyLightDto("GroveStreetFamilly", 2354685748L),
                new CompanyLightDto("BeautifulCompany", 9568545875L));
        given().relaxedHTTPSValidation().baseUri(baseUri).when().header(new Header("Authorization", "Bearer " + adminToken))
                .get("company/companies-info").then().statusCode(200).and().body(containsString(new Gson().toJson(companies)));
    }

/*    private String getAuthToken(String login, String password) {
        AuthenticateDto authenticateDto = new AuthenticateDto(login, password);

        Response response = given().relaxedHTTPSValidation().baseUri(authBaseUri)
                .contentType(ContentType.JSON)
                .body(authenticateDto)
                .when()
                .post("/sign-in");
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        return response.getBody().asString();
    }*/
    private String getAuthToken(String login, String password) {
        return JWTHandler.createToken(Map.of("accessLevels", List.of("ADMINISTRATOR", "BUSINESS_WORKER", "MODERATOR")), "rbranson");
    }

}