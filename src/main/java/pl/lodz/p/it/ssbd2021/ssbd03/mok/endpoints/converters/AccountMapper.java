package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.converters;

import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.wrappers.LanguageTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.AccountManagerException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.ETagException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccessLevelDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccountDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels.AdministratorDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels.BusinessWorkerDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels.ClientDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels.ModeratorDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.ACCESS_LEVEL_DOES_NOT_EXIST_ERROR;

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
                client.getLogin(), client.getEmail().toLowerCase(), DigestUtils.sha256Hex(client.getPassword()),
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
                bw.getLogin(), bw.getEmail().toLowerCase(), DigestUtils.sha256Hex(bw.getPassword()),
                new LanguageTypeWrapper(bw.getLanguageType()));
    }

    /**
     * Mapuje obiekt klasy Account na obiekt przesyłowy klasy AccountDto
     *
     * @param account Konto poddawane konwersji
     * @return Reprezentacja obiektu przesyłowego DTO konta
     */
    public static AccountDto toAccountDto(Account account) {
        return new AccountDto(account.getLogin(), account.getFirstName(), account.getSecondName(),
                account.isDarkMode(), account.getEmail(), account.getLanguageType().getName(),
                account.getAccessLevels().stream()
                        .map(AccessLevel::getAccessLevelType)
                        .collect(Collectors.toSet()), account.getVersion());
    }

    /**
     * @param account Konto poddawane konwersji
     * @return Reprezentacja obiektu przesyłowego DTO konta zawierający login, e-mail ,poziomy dostępu oraz informacje
     * czy dane konto jest aktywne
     */
    public static AccountDtoForList toAccountListDto(Account account) throws BaseAppException {
        return new AccountDtoForList(account.getLogin(),
                account.getFirstName(), account.getSecondName(),
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
    public static AccountDetailsViewDto toAccountDetailsViewDto(Account account) throws BaseAppException {
        return new AccountDetailsViewDto(account.getFirstName(), account.getSecondName(), account.isDarkMode(), account.getLogin(),
                account.getEmail(), account.isConfirmed(), account.isActive(), account.getLanguageType().getName(),
                account.getAccessLevels().stream()
                        .map(AccountMapper::toAccessLevelDetailsViewDto)
                        .collect(Collectors.toSet()),
                account.getVersion());
    }

    /**
     * Mapuje obiekt klasy Account na obiekt przesyłowy klasy AccountDetailsViewDto filtrując tylko aktywne poziomy dostępu
     *
     * @param account Konto poddawane konwersji
     * @return Reprezentacja konta w postaci obiektu przesyłowego AccountDetailsViewDto
     */
    public static AccountDetailsViewDto toAccountDetailsViewFilteringAccessLevels(Account account) throws BaseAppException {
        return new AccountDetailsViewDto(account.getFirstName(), account.getSecondName(), account.isDarkMode(), account.getLogin(),
                account.getEmail(), account.isConfirmed(), account.isActive(), account.getLanguageType().getName(),
                getAuthorizedAccessLevels(account)
                        .map(AccountMapper::toAccessLevelDetailsViewDto)
                        .collect(Collectors.toSet()),
                account.getVersion());
    }

    private static AccessLevelDetailsViewDto toAccessLevelDetailsViewDto(AccessLevel accessLevel) {
        switch (accessLevel.getAccessLevelType()) {
            case CLIENT:
                Client client = (Client) accessLevel;
                return new ClientDetailsViewDto(accessLevel.isEnabled(), toAddressDto(client.getHomeAddress()), client.getPhoneNumber(), client.getVersion());
            case BUSINESS_WORKER:
                BusinessWorker businessWorker = (BusinessWorker) accessLevel;
                return new BusinessWorkerDetailsViewDto(businessWorker.isEnabled(), businessWorker.getPhoneNumber(),
                        businessWorker.isConfirmed(), businessWorker.getCompany().getName(), businessWorker.getVersion());
            case MODERATOR:
                return new ModeratorDetailsViewDto(accessLevel.isEnabled(), accessLevel.getVersion());
            case ADMINISTRATOR:
                return new AdministratorDetailsViewDto(accessLevel.isEnabled(), accessLevel.getVersion());
            default:
                return null; // Statement will never execute unless new AccessLevel ENUM is added to the model
        }
    }

    private static Stream<AccessLevel> getAuthorizedAccessLevels(Account account) {
        return account.getAccessLevels().stream()
                .filter(accessLevel -> {
                    if (accessLevel instanceof BusinessWorker) {
                        BusinessWorker bw = (BusinessWorker) accessLevel;
                        return bw.isEnabled() && bw.isConfirmed();
                    }
                    return accessLevel.isEnabled();
                });
    }

    private static AddressDto toAddressDto(Address address) {
        return new AddressDto(address.getHouseNumber(), address.getStreet(),
                address.getPostalCode(), address.getCity(), address.getCountry());
    }

    /**
     * Mapuje obiekt klasy account na obiekt DTO
     *
     * @param account Konto poddawane konwersji
     * @return Reprezentacja obiektu przesyłowego DTO konta zawierający login, wersje, imię, nazwisko, numer telefonu, adres oraz konto modyfikujące
     */
    public static OtherClientChangeDataDto accountDtoForClientDataChange(Account account) {

        Client fromClient = (Client) account.getAccessLevels().stream().filter(accessLevel -> accessLevel.getAccessLevelType().equals(AccessLevelType.CLIENT)).collect(Collectors.toList()).get(0);
        OtherAddressChangeDto addressChangeDto = new OtherAddressChangeDto(fromClient.getHomeAddress().getHouseNumber(), fromClient.getHomeAddress().getStreet(),
                fromClient.getHomeAddress().getPostalCode(), fromClient.getHomeAddress().getCity(), fromClient.getHomeAddress().getCountry());
        return new OtherClientChangeDataDto(account.getLogin(), account.getVersion(), fromClient.getPhoneNumber(), addressChangeDto, fromClient.getVersion());

    }

    /**
     * Mapuje obiekt klasy account na obiekt DTO
     *
     * @param account Konto poddawane konwersji
     * @return Reprezentacja obiektu przesyłowego DTO konta zawierający login, wersje, imię, nazwisko, numer telefonu oraz konto modyfikujące
     */
    public static OtherBusinessWorkerChangeDataDto accountDtoForBusinnesWorkerDataChange(Account account) {

        BusinessWorker fromBusinessWorker = (BusinessWorker) account.getAccessLevels().stream().filter(accessLevel -> accessLevel.getAccessLevelType().equals(AccessLevelType.BUSINESS_WORKER)).collect(Collectors.toList()).get(0);
        return new OtherBusinessWorkerChangeDataDto(account.getLogin(), account.getVersion(),
                fromBusinessWorker.getPhoneNumber(), fromBusinessWorker.getVersion()
        );

    }

    /**
     * Mapuje obiekt klasy dto na obiekt klasy modelu Account który jest wykorzystany do utrwalenia danych w bazie
     *
     * @param otherAccountChangeDataDto obiekt klasy DTO
     * @return obiekt klasy modelu który prezentuje konto o poziomie dostępu moderator lub administrator
     */
    public static Account extractAccountFromOtherAccountChangeDataDto(OtherAccountChangeDataDto otherAccountChangeDataDto) {
        Account account = new Account();
        account.setLogin(otherAccountChangeDataDto.getLogin());
        account.setVersion(otherAccountChangeDataDto.getVersion());
        account.setFirstName(otherAccountChangeDataDto.getNewFirstName());
        account.setSecondName(otherAccountChangeDataDto.getNewSecondName());
        return account;
    }

    private static void setAccountChangeDataDtoFields(Account account, AccountChangeDataDto accountChangeDataDto, LocalDateTime time) {
        account.setLogin(accountChangeDataDto.getLogin());
        account.setVersion(accountChangeDataDto.getVersion());
        account.setFirstName(accountChangeDataDto.getNewFirstName());
        account.setSecondName(accountChangeDataDto.getNewSecondName());
        account.setAlteredBy(account);
        account.setAlterType(account.getAlterType());
        account.setLastAlterDateTime(time);
    }

    /**
     * Mapuje obiekt klasy dto na obiekt klasy modelu Account który jest wykorzystany do utrwalenia danych w bazie
     *
     * @param clientChangeDataDto obiekt klasy DTO
     * @return obiekt klasy modelu który prezentuje konto o poziomie dostępu clienta
     */
    public static Account extractAccountFromClientChangeDataDto(ClientChangeDataDto clientChangeDataDto) {
        LocalDateTime now = LocalDateTime.now();

        Account account = new Account();
        setAccountChangeDataDtoFields(account, clientChangeDataDto, now);

        Address address = new Address(
                clientChangeDataDto.getNewAddress().getHouseNumber(),
                clientChangeDataDto.getNewAddress().getStreet(),
                clientChangeDataDto.getNewAddress().getPostalCode(),
                clientChangeDataDto.getNewAddress().getCity(),
                clientChangeDataDto.getNewAddress().getCountry()
        );

        Client client = new Client(address, clientChangeDataDto.getNewPhoneNumber());

        account.setAccessLevel(client);

        return account;
    }

    public static Account extractAccountFromBusinessWorkerChangeDataDto(BusinessWorkerChangeDataDto businessWorkerChangeDataDto) {
        LocalDateTime now = LocalDateTime.now();

        Account account = new Account();

        setAccountChangeDataDtoFields(account, businessWorkerChangeDataDto, now);

        BusinessWorker businessWorker = new BusinessWorker(businessWorkerChangeDataDto.getNewPhoneNumber(), true);

        account.setAccessLevel(businessWorker);

        return account;
    }

    public static Account extractAccountFromModeratorChangeDataDto(ModeratorChangeDataDto moderatorChangeDataDto) {
        LocalDateTime now = LocalDateTime.now();

        Account account = new Account();

        setAccountChangeDataDtoFields(account, moderatorChangeDataDto, now);

        return account;
    }

    public static Account extractAccountFromAdministratorChangeDataDto(AdministratorChangeDataDto administratorChangeDataDto) {
        LocalDateTime now = LocalDateTime.now();

        Account account = new Account();

        setAccountChangeDataDtoFields(account, administratorChangeDataDto, now);

        return account;
    }


    public static AccessLevel getAccessLevel(Account from, AccessLevelType target) throws BaseAppException {
        Optional<AccessLevel> optionalAccessLevel = from.getAccessLevels().stream()
                .filter(accessLevel -> accessLevel.getAccessLevelType().equals(target)).findAny();

        return optionalAccessLevel.orElseThrow(() -> new AccountManagerException(ACCESS_LEVEL_DOES_NOT_EXIST_ERROR));
    }

    public static ClientDto toClientDto(Account account) throws BaseAppException {
        Client client = (Client) getAccessLevel(account, AccessLevelType.CLIENT);

        return new ClientDto(
                account.getLogin(),
                account.getFirstName(),
                account.getSecondName(),
                account.getEmail(),
                account.getLanguageType().getName(),
                toAddressDto(client.getHomeAddress()),
                client.getPhoneNumber(),
                account.getVersion());
    }

    public static BusinessWorkerDto toBusinessWorkerDto(BusinessWorker businessWorker) {

        Account account = businessWorker.getAccount();
        return new BusinessWorkerDto(
                account.getLogin(),
                account.getFirstName(),
                account.getSecondName(),
                account.getEmail(),
                account.getLanguageType().getName(),
                businessWorker.getPhoneNumber(),
                account.getVersion()
        );
    }

    public static BusinessWorkerWithCompanyDto toBusinessWorkerWithCompanyDto(BusinessWorker businessWorker) throws ETagException {

        Account account = businessWorker.getAccount();
        return new BusinessWorkerWithCompanyDto(
                account.getLogin(),
                account.getFirstName(),
                account.getSecondName(),
                account.getEmail(),
                account.getLanguageType().getName(),
                businessWorker.getPhoneNumber(),
                account.getVersion(),
                businessWorker.getCompany().getName(),
                businessWorker.getCompany().getPhoneNumber()

        );
    }

    /**
     * Mapuje oiekt klasy account na obiekt dto BusinessWorkerWithCompanyDto
     *
     * @param account konto użytkownika podawane konwersji
     * @return obiekt dto BusinessWorkerWithCompanyDto
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    public static BusinessWorkerWithCompanyDto toBusinessWorkerWithCompanyDto(Account account) throws BaseAppException {
        BusinessWorker businessWorker = (BusinessWorker) getAccessLevel(account, AccessLevelType.BUSINESS_WORKER);
        return new BusinessWorkerWithCompanyDto(
                account.getLogin(),
                account.getFirstName(),
                account.getSecondName(),
                account.getEmail(),
                account.getLanguageType().getName(),
                businessWorker.getPhoneNumber(),
                businessWorker.getVersion(),
                businessWorker.getCompany().getName(),
                businessWorker.getCompany().getPhoneNumber()
        );
    }

    public static ModeratorDto toModeratorDto(Account account) {
        return new ModeratorDto(
                account.getLogin(),
                account.getFirstName(),
                account.getSecondName(),
                account.getEmail(),
                account.getLanguageType().getName(),
                account.getVersion()
        );
    }

    public static AdministratorDto toAdministratorDto(Account account) {
        return new AdministratorDto(
                account.getLogin(),
                account.getFirstName(),
                account.getSecondName(),
                account.getEmail(),
                account.getLanguageType().getName(),
                account.getVersion()
        );
    }

    public static AccountMetadataDto toAccountMetadataDto(Account account) {
        return new AccountMetadataDto(account.getCreationDateTime(),
                account.getLastAlterDateTime(),
                account.getCreatedBy().getLogin(),
                account.getAlteredBy().getLogin(),
                account.getAlterType().getName(),
                account.getVersion(),
                account.getLastIncorrectAuthenticationDateTime(),
                account.getLastIncorrectAuthenticationLogicalAddress(),
                account.getLastCorrectAuthenticationDateTime(),
                account.getLastCorrectAuthenticationLogicalAddress(),
                account.getNumberOfAuthenticationFailures(),
                account.getLanguageType().getName().toString());
    }
}
