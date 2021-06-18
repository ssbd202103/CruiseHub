package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Header;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations.RemoveClientReservationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.security.JWTHandler;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class ReservationControllerIT {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String reservationBaseUri;

    ReservationControllerIT() {
        Properties securityProperties = PropertiesReader.getSecurityProperties();
        reservationBaseUri = securityProperties.getProperty("app.baseurl") + "/api/reservation";
    }

//    @Test
//    public void removeClientReservation() throws Exception {
//        String adminToken = this.getAuthToken("rbranson", "abcABC123*");
//        RemoveClientReservationDto reservationDto = new RemoveClientReservationDto(0, "ab3e9fd0-bbac-11eb-8529-0242ac130003", "rbranson");
//        given().relaxedHTTPSValidation()
//                .baseUri(reservationBaseUri)
//                .contentType(MediaType.APPLICATION_JSON)
//                .header(new Header("Authorization", "Bearer " + adminToken))
//                .delete("/" + reservationDto.getReservationVersion() +
//                        "/" + reservationDto.getClientLogin() +
//                        "/" + reservationDto.getReservationUuid())
//                .then().statusCode(204);
//    }

    private String getAuthToken(String login, String password) {
        return JWTHandler.createToken(Map.of("accessLevels", List.of("ADMINISTRATOR", "BUSINESS_WORKER", "MODERATOR")), "rbranson");
    }

}
