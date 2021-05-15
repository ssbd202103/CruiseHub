package pl.lodz.p.it.ssbd2021.ssbd03.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.JWTException;
import pl.lodz.p.it.ssbd2021.ssbd03.testModel.mok.dto.TestAccountDto;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class JWTHandlerTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void createTokenTest() throws JWTException {
        List<TestAccountDto> accounts = getSampleAccounts();

        for (TestAccountDto account : accounts) {
            Map<String, Object> claims = mapper.convertValue(account, Map.class);
            String subject = "184";

            String token = JWTHandler.createToken(claims, subject);
            Map<String, Claim> decodedClaims = JWTHandler.getClaimsFromToken(token);

            assertEquals(subject, decodedClaims.get("sub").asString());
            assertEquals(account.getLogin(), decodedClaims.get("login").asString());
            assertEquals(account.getFirstName(), decodedClaims.get("firstName").asString());
            assertEquals(account.getSecondName(), decodedClaims.get("secondName").asString());
            assertEquals(account.getEmail(), decodedClaims.get("email").asString());
            assertEquals(account.getLanguageType().name(), decodedClaims.get("languageType").asString());
            assertEquals(account.getAccessLevels().stream().map(AccessLevelType::name).collect(Collectors.toList()), decodedClaims.get("accessLevels").asList(String.class));
        }
    }

    @Test
    void verifyTokenTest() {
        List<TestAccountDto> accounts = getSampleAccounts();

        for (TestAccountDto account : accounts) {
            Map<String, Object> claims = mapper.convertValue(account, Map.class);

            String token = JWTHandler.createToken(claims, "152");
            assertDoesNotThrow(() -> JWTHandler.validateToken(token));

            String editedToken = token.replaceAll(".{3}(?=$)", "zzz"); //changing last 3 signs of token's signature
            assertThrows(JWTException.class, () -> JWTHandler.validateToken(editedToken));
        }
    }

    @Test
    public void refreshTokenTest() throws InterruptedException {
        TestAccountDto account = getSampleAccounts().get(0);
        Map<String, Object> claims = mapper.convertValue(account, Map.class);
        String subject = "184";

        String token = JWTHandler.createToken(claims, subject);
        DecodedJWT decodedToken = JWT.decode(token);

        Thread.sleep(2000); //there should be more efficient way to test it

        String refreshedToken = JWTHandler.refreshToken(token);
        DecodedJWT decodedRefreshToken = JWT.decode(refreshedToken);

        for (Entry<String, Claim> entry : decodedToken.getClaims().entrySet()) {

            if (List.of("exp", "iat").contains(entry.getKey())) {
                assertTrue(entry.getValue().asDate().before(decodedRefreshToken.getClaim(entry.getKey()).asDate()));
            } else if (entry.getKey().equals("jti")) {
                assertNotEquals(entry.getValue().as(Object.class), decodedRefreshToken.getClaim(entry.getKey()).as(Object.class));
            } else {
                assertEquals(entry.getValue().as(Object.class), decodedRefreshToken.getClaim(entry.getKey()).as(Object.class));
            }

        }
    }

    private List<TestAccountDto> getSampleAccounts() {
        return List.of(
                new TestAccountDto("admin1", "Michał", "Konieczny", "michal.konieczny@gmail.com",
                        LanguageType.PL, Set.of(AccessLevelType.ADMINISTRATOR, AccessLevelType.CLIENT)),
                new TestAccountDto("mariola123", "Mariola", "Niebieska", "mariolka374@gmail.com",
                        LanguageType.PL, Set.of(AccessLevelType.CLIENT)),
                new TestAccountDto("mmMod944_", "Mariusz", "Skrowronek", "mariusz.skowronek@hotmail.com",
                        LanguageType.PL, Set.of(AccessLevelType.CLIENT, AccessLevelType.BUSINESS_WORKER))
        );
    }
}
