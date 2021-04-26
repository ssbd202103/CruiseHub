package pl.lodz.p.it.ssbd2021.ssbd03.security;


import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.testModel.mok.dto.TestAccountDto;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ETagTest {


    @Test
    void verifyEtag() {
        List<TestAccountDto> accounts = getSampleAccounts();
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(accounts.get(0));
        assertEquals(EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, accounts.get(0)), true);

    }

    @Test
    void verifyChangedObjectETag() {
        List<TestAccountDto> accounts = getSampleAccounts();
        String etag = EntityIdentitySignerVerifier.calculateEntitySignature(accounts.get(0));
        accounts.get(0).setEmail("andrzej.konieczny@gmail.com");
        assertEquals(EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, accounts.get(0)), false);

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
