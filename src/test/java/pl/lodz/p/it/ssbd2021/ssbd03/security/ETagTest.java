package pl.lodz.p.it.ssbd2021.ssbd03.security;


import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.testModel.mok.dto.TestAccountDto;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ETagTest {


    @Test
    void verifyEtag_SUCCESS() {
        List<TestAccountDto> accounts = getSampleAccounts();
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(accounts.get(0));
        assertTrue(EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, accounts.get(0)));

    }

    @Test
    void verifyChangedObjectETag_UNSUCCESS() {
        List<TestAccountDto> accounts = getSampleAccounts();
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(accounts.get(0));
        accounts.get(0).setEmail("andrzej.konieczny@gmail.com");
        assertFalse(EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, accounts.get(0)));

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
