package pl.lodz.p.it.ssbd2021.ssbd03;

import static io.restassured.RestAssured.given;

public class Test {

    @org.junit.jupiter.api.Test
    public void exampleTest() {

        given().when().get("https://www.google.com/search?channel=fs&client=ubuntu&q=rest+assured+example").then().statusCode(200);
    }

}
