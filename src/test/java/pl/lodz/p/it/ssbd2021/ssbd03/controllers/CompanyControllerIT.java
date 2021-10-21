package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.Header;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.security.JWTHandler;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;

import java.util.*;

import static io.restassured.RestAssured.given;
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
    void getAllCompaniesInfo_SUCCESS() throws JsonProcessingException {
        String adminToken = getAuthToken("rbranson", "abcABC123*");
        Set<CompanyLightDto> companies = Set.of(
                new CompanyLightDto("FirmaJez", 1265485965L),
                new CompanyLightDto("GroveStreetFamilly", 2354685748L),
                new CompanyLightDto("BeautifulCompany", 9568545875L));
        Companies outcome = given().relaxedHTTPSValidation().baseUri(baseUri).when().header(new Header("Authorization", "Bearer " + adminToken))
                .get("company/companies-info").then().statusCode(200).and().extract().as(Companies.class);
        assertEquals(outcome, companies);
    }

    private String getAuthToken(String login, String password) {
        return JWTHandler.createToken(Map.of("accessLevels", List.of("ADMINISTRATOR", "BUSINESS_WORKER", "MODERATOR")), "rbranson");
    }

    private static class Companies extends HashSet<CompanyLightDto> {
    }
}