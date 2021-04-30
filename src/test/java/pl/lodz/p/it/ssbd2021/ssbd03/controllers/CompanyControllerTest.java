package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.google.gson.Gson;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CompanyLightDto;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

class CompanyControllerTest {
    private static final String baseUri = "http://localhost:8080/api/";

    @Test
    void getAllCompaniesInfo_SUCCESS() {

        List<CompanyLightDto> companies = Arrays.asList(new CompanyLightDto("FirmaJez", 1265485965L),
                new CompanyLightDto("GroveStreetFamilly", 2354685748L),
                new CompanyLightDto("BeautifulCompany", 9568545875L));

        given().baseUri(baseUri).when().get("company/companiesinfo").then().statusCode(200).and().body(containsString(new Gson().toJson(companies)));
    }
}