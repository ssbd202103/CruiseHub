package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.converters;

import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.wrappers.LanguageTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDtoForList;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccessLevelDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccountDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels.AdministratorDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels.BusinessWorkerDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels.ClientDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels.ModeratorDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;

import java.util.stream.Collectors;

/**
 * Klasa która zajmuje się mapowaniem obiektów klas dto na obiekty klas modelu
 */
public class AccountMapper {
    private AccountMapper() {
    }

    /**
     * Mapuje obiekt klasy dto na obiekt klasy modelu Client który jest wykorzystany do utrwalenia danych w bazie
     *
     * @param client obiekt klasy dto
     * @return obiekt klasy modelu który przezentuje poziom dostępu klient
     */
    public static Client extractClientFromClientForRegistrationDto(ClientForRegistrationDto client) {
        return new Client(extractAddressFromClientForRegistrationDto(client), client.getPhoneNumber());
    }

    /**
     * Mapuje obiekt klasy dto na obiekt klasy modelu Account który jest wykorzystany do utrwalenia danych w bazie
     *
     * @param client obiekt klasy dto
     * @return obiekt klasy modelu który prezentuje Konto
     */
    public static Account extractAccountFromClientForRegistrationDto(ClientForRegistrationDto client) {
        return new Account(client.getFirstName(), client.getSecondName(),
                client.getLogin(), client.getEmail(), DigestUtils.sha256Hex(client.getPassword()),
                new LanguageTypeWrapper(client.getLanguageType()));
    }

    /**
     * Mapuje obiekt klasy dto na obiekt klasy modelu Address który jest wykorzystany do utrwalenia danych w bazie
     *
     * @param client obiekt klasy dto
     * @return obiekt klasy modelu który prezentuje adres
     */
    public static Address extractAddressFromClientForRegistrationDto(ClientForRegistrationDto client) {
        return new Address(client.getAddressDto().getHouseNumber(),
                client.getAddressDto().getStreet(),
                client.getAddressDto().getPostalCode(),
                client.getAddressDto().getCity(),
                client.getAddressDto().getCountry());
    }

    /**
     * Mapuje obiekt klasy dto na obiekt klasy modelu Address który jest wykorzystany do utrwalenia danych w bazie
     *
     * @param bw obiekt klasy dto
     * @return obiekt klasy modelu który prezentuje poziom dostępu pracownik firmy
     */
    public static BusinessWorker extractBusinessWorkerFromBusinessWorkerForRegistrationDto(BusinessWorkerForRegistrationDto bw) {
        return new BusinessWorker(bw.getPhoneNumber(), true);
    }

    /**
     * Mapuje obiekt klasy dto na obiekt klasy modelu Account który jest wykorzystany do utrwalenia danych w bazie
     *
     * @param bw obiekt klasy dto
     * @return obiekt klasy modelu który prezentuje konto
     */
    public static Account extractAccountFromBusinessWorkerForRegistrationDto(BusinessWorkerForRegistrationDto bw) {
        return new Account(bw.getFirstName(), bw.getSecondName(),
                bw.getLogin(), bw.getEmail(), DigestUtils.sha256Hex(bw.getPassword()),
                new LanguageTypeWrapper(bw.getLanguageType()));
    }

    /**
     * Mapuje obiekt klasy Account na obiekt przesyłowy klasy AccountDto
     *
     * @param account Konto poddawane konwersji
     * @return Reprezentacja obiektu przesyłowego DTO konta
     */
    public static AccountDto toAccountDto(Account account) {
        return new AccountDto(account.getLogin(), account.getFirstName(),
                account.getSecondName(), account.getEmail(), account.getLanguageType().getName(),
                account.getAccessLevels().stream()
                        .map(AccessLevel::getAccessLevelType)
                        .collect(Collectors.toSet()), account.getVersion());
    }

    /**
     * @param account Konto poddawane konwersji
     * @return Reprezentacja obiektu przesyłowego DTO konta zawierający login, e-mail ,poziomy dostępu oraz informacje
     * czy dane konto jest aktywne
     */
    public static AccountDtoForList toAccountListDto(Account account) {
        return new AccountDtoForList(account.getLogin(),
                account.getEmail(), account.isActive(),
                account.getVersion(),
                account.getAccessLevels().stream()
                        .map(AccessLevel::getAccessLevelType)
                        .collect(Collectors.toSet()));
    }

    /**
     * Mapuje obiekt klasy Account na obiekt przesyłowy klasy AccountDetailsViewDto
     *
     * @param account Konto poddawane konwersji
     * @return Reprezentacja konta w postaci obiektu przesyłowego AccountDetailsViewDto
     */
    public static AccountDetailsViewDto toAccountDetailsViewDto(Account account) {
        return new AccountDetailsViewDto(account.getFirstName(), account.getSecondName(), account.getLogin(),
                account.getEmail(), account.isConfirmed(), account.isActive(), account.getLanguageType().getName(),
                account.getAccessLevels().stream()
                        .map(AccountMapper::toAccessLevelDetailsViewDto)
                        .collect(Collectors.toSet()), account.getVersion());
    }

    private static AccessLevelDetailsViewDto toAccessLevelDetailsViewDto(AccessLevel accessLevel) {
        switch (accessLevel.getAccessLevelType()) {
            case CLIENT:
                Client client = (Client) accessLevel;
                return new ClientDetailsViewDto(accessLevel.isEnabled(), toAddressDto(client.getHomeAddress()), client.getPhoneNumber());
            case BUSINESS_WORKER:
                BusinessWorker businessWorker = (BusinessWorker) accessLevel;
                return new BusinessWorkerDetailsViewDto(businessWorker.isEnabled(), businessWorker.getPhoneNumber(),
                        businessWorker.getConfirmedByBusinessWorker(), businessWorker.getCompany().getName());
            case MODERATOR:
                return new ModeratorDetailsViewDto(accessLevel.isEnabled());
            case ADMINISTRATOR:
                return new AdministratorDetailsViewDto(accessLevel.isEnabled());
            default:
                return null; // Statement will never execute unless new AccessLevel ENUM is added to the model
        }
    }

    private static AddressDto toAddressDto(Address address) {
        return new AddressDto(address.getHouseNumber(), address.getStreet(),
                address.getPostalCode(), address.getCity(), address.getCountry());
    }
}
