package pl.lodz.p.it.ssbd2021.ssbd03.mok.mappers;

import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Administrator;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Moderator;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.wrappers.LanguageTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.converters.AccountMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AccountMapperTest {

    @Test
    void toAccountDtoTest() {
        List<Account> accounts = getSampleAccounts();

        for (Account account : accounts) {
            AccountDto accountDto = AccountMapper.toAccountDto(account);

            assertEquals(accountDto.getEmail(), account.getEmail());
            assertEquals(accountDto.getFirstName(), account.getFirstName());
            assertEquals(accountDto.getSecondName(), account.getSecondName());
            assertEquals(accountDto.getLanguageType(), account.getLanguageType().getName());
            assertEquals(accountDto.getAccessLevels(), account.getAccessLevels().stream().map(AccessLevel::getAccessLevelType).collect(Collectors.toSet()));
        }
    }


    private static List<Account> getSampleAccounts() {
        Account account1 = new Account("Michał", "Kolanko", "michal123", "michu743@gmail.com",
                "5d8786696f7776169055f8b689aa9a02bc88060a2c13ddd2c602369a96bfbe17", true, true,
                new LanguageTypeWrapper(LanguageType.PL));
        account1.setAccessLevel(new Moderator(true));

        Account account2 = new Account("Julia", "Niebieska", "julanta88", "julia.niebieska@gmail.com",
                "8b689aa9a026fasdfasdf9aas302bc88112060a2c13ddd2c6202369a96bfbe17", true, true,
                new LanguageTypeWrapper(LanguageType.PL));
        account2.setAccessLevel(new Client(new Address("20", "Łęczycka", "283-138", "Warsaw", "Poland"), "473-173-183", true));

        Account account3 = new Account("John", "Wayne", "batman001", "john.wayne@waynecorp.com",
                "1532e76dbe9d43d0dea98c331ca5ae8a65c5e8e8b99d3e2a42ae989356f6242a", true, true,
                new LanguageTypeWrapper(LanguageType.EN));
        account3.setAccessLevel(new Administrator(true));
        account3.setAccessLevel(new Client(new Address("174", "Wallstreet", "123-1382", "New York", "USA"), "473-173-183", true));

        return Arrays.asList(account1, account2, account3);
    }
}