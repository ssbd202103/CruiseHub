package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.converters;

import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Administrator;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Moderator;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.wrappers.LanguageTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.AdministratorForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ModeratorForRegistrationDto;

import java.time.LocalDateTime;

/**
 * Klasa która zajmuje się mapowaniem obiektów klas dto na obiekty klas modelu
 */

@NoArgsConstructor
public class AccountMapper {

    /**
     * Mapuje obiekt klasy dto na obiekt klasy modelu Client który jest wykorzystany do utrwalenia danych w bazie
     *
     * @param client obiekt klasy dto
     * @return obiekt klasy modelu który przezentuje poziom dostępu klient
     */
    public static Client extractClientFromClientForRegistrationDto(ClientForRegistrationDto client) {
        return new Client(client.getPhoneNumber());
    }

    /**
     * Mapuje obiekt klasy dto na obiekt klasy modelu Account który jest wykorzystany do utrwalenia danych w bazie
     *
     * @param client obiekt klasy dto
     * @return obiekt klasy modelu który prezentuje Konto
     */
    public static Account extractAccountFromClientForRegistrationDto(ClientForRegistrationDto client) {
        return new Account(client.getFirstName(), client.getSecondName(),
                client.getLogin(), client.getEmail(), client.getPassword(),
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
                bw.getLogin(), bw.getEmail(), bw.getPassword(),
                new LanguageTypeWrapper(bw.getLanguageType()));
    }

    /**
     * Mapuje obiekt klasy dto na obiekt klasy modelu Account który jest wykorzystany do utrwalenia danych w bazie
     *
     * @param administrator obiekt klasy dto
     * @return biekt klasy modelu który prezentuje konto
     */
    public static Account extractAccountFromAdministratorForRegistrationDto(AdministratorForRegistrationDto administrator) {
        return new Account(administrator.getFirstName(), administrator.getSecondName(),
                administrator.getLogin(), administrator.getEmail(), administrator.getPassword(),
                new LanguageTypeWrapper(administrator.getLanguageType()));
    }

    /**
     * Mapuje obiekt klasy dto na obiekt klasy modelu Account który jest wykorzystany do utrwalenia danych w bazie
     *
     * @param moderator obiekt klasy dto
     * @return biekt klasy modelu który prezentuje konto
     */
    public static Account extractAccountFromModeratorForRegistrationDto(ModeratorForRegistrationDto moderator) {
        return new Account(moderator.getFirstName(), moderator.getSecondName(),
                moderator.getLogin(), moderator.getEmail(), moderator.getPassword(),
                new LanguageTypeWrapper(moderator.getLanguageType()));
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

    private static LocalDateTime getNowLocalDateTime() {
        return LocalDateTime.now();
    }

    public static Account extractAccountFromClientChangeDataDto(ClientChangeDataDto clientChangeDataDto) {
        LocalDateTime now = getNowLocalDateTime();

        Account account = new Account();
        setAccountChangeDataDtoFields(account, clientChangeDataDto, now);

        Address address = new Address(
                clientChangeDataDto.getNewAddress().getNewHouseNumber(),
                clientChangeDataDto.getNewAddress().getNewStreet(),
                clientChangeDataDto.getNewAddress().getNewPostalCode(),
                clientChangeDataDto.getNewAddress().getNewCity(),
                clientChangeDataDto.getNewAddress().getNewCountry()
        );

        Client client = new Client(address, clientChangeDataDto.getNewPhoneNumber());

        account.setAccessLevel(client);

        return account;
    }

    public static Account extractAccountFromBusinessWorkerChangeDataDto(BusinessWorkerChangeDataDto businessWorkerChangeDataDto) {
        LocalDateTime now = getNowLocalDateTime();

        Account account = new Account();

        setAccountChangeDataDtoFields(account, businessWorkerChangeDataDto, now);

        BusinessWorker businessWorker = new BusinessWorker(businessWorkerChangeDataDto.getNewPhoneNumber(), true);

        account.setAccessLevel(businessWorker);

        return account;
    }

    public static Account extractAccountFromModeratorChangeDataDto(ModeratorChangeDataDto moderatorChangeDataDto) {
        LocalDateTime now = getNowLocalDateTime();

        Account account = new Account();

        setAccountChangeDataDtoFields(account, moderatorChangeDataDto, now);

        return account;
    }

    public static Account extractAccountFromAdministratorChangeDataDto(AdministratorChangeDataDto administratorChangeDataDto) {
        LocalDateTime now = getNowLocalDateTime();

        Account account = new Account();

        setAccountChangeDataDtoFields(account, administratorChangeDataDto, now);

        return account;
    }
}
