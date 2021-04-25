package pl.lodz.p.it.ssbd2021.ssbd03.mok.mappers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;

import java.util.stream.Collectors;

/**
 * Klasa odpowiadająca za konwersję obiektów kont na obiekty przesyłowe DTO
 */
public class AccountMapper {

    /**
     * @param account Konto poddawane konwersji
     * @return Reprezentacja obiektu przesyłowego DTO konta
     */
    public static AccountDto toAccountDto(Account account) {
        return new AccountDto(account.getLogin(), account.getFirstName(),
                account.getSecondName(), account.getEmail(), account.getLanguageType().getName(),
                account.getAccessLevels().stream()
                        .map(AccessLevel::getAccessLevelType)
                        .collect(Collectors.toSet()));
    }
}
