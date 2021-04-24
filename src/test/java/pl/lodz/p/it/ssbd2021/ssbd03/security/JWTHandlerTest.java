package pl.lodz.p.it.ssbd2021.ssbd03.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class JWTHandlerTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void createTokenTest() {
        List<AccountDto> accounts = getSampleAccounts();

        for (AccountDto account : accounts) {
            Map<String, Object> claims = mapper.convertValue(account, Map.class);
            String subject = "184";

            String token = JWTHandler.createToken(claims, "184");
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
        List<AccountDto> accounts = getSampleAccounts();

        for (AccountDto account : accounts) {
            Map<String, Object> claims = mapper.convertValue(account, Map.class);

            String token = JWTHandler.createToken(claims, "Szymon");
            assertDoesNotThrow(() -> JWTHandler.validateToken(token));

            String editedToken = token.replaceAll(".{3}(?=$)", "zzz"); //changing last 3 signs of token's signature
            assertThrows(JWTVerificationException.class, () -> JWTHandler.validateToken(editedToken));
        }
    }

    private List<AccountDto> getSampleAccounts() {
        return List.of(
                new AccountDto("admin1", "Michał", "Konieczny", "michal.konieczny@gmail.com",
                        LanguageType.PL, Set.of(AccessLevelType.ADMINISTRATOR, AccessLevelType.CLIENT)),
                new AccountDto("mariola123", "Mariola", "Niebieska", "mariolka374@gmail.com",
                        LanguageType.PL, Set.of(AccessLevelType.CLIENT)),
                new AccountDto("mmMod944_", "Mariusz", "Skrowronek", "mariusz.skowronek@hotmail.com",
                        LanguageType.PL, Set.of(AccessLevelType.CLIENT, AccessLevelType.BUSINESS_WORKER))
        );
    }
}