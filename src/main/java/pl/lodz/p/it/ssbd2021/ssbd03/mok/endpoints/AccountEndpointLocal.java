package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.AccountChangeEmailDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.AdministratorChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.BusinessWorkerChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.ClientChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.ModeratorChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.AdministratorForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ModeratorForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;

import javax.ejb.Local;
import javax.persistence.OptimisticLockException;


/**
 * Klasa która zajmuje się growadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z kontami użytkowników i poziomami dostępu, oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Local
public interface AccountEndpointLocal {
    /**
     * Mapuje obiekt dto na obiekty modelu
     *
     * @param clientForRegistrationDto obiekt klasy dto która przechowuje wszystkie niezbędne pola do stworzenia nowego konta użytkownika z przypisanym poziomem dostępu Klient
     */
    void createClientAccount(ClientForRegistrationDto clientForRegistrationDto);

    /**
     * Mapuje obiekt dto na obiekty modelu
     *
     * @param businessWorkerForRegistrationDto obiekt klasy dto która przechowuje wszystkie niezbędne pola do stworzenia nowego konta użytkownika z przypisanym poziomem dostępu Pracownik Firmy
     */
    void createBusinessWorkerAccount(BusinessWorkerForRegistrationDto businessWorkerForRegistrationDto);

    /**
     * Mapuje obiekt dto na obiekty modelu
     *
     * @param AdministratorForRegistrationDto obiekt klasy dto która przechowuje wszystkie niezbędne pola do stworzenia nowego konta użytkownika z przypisanym poziomem dostępu Administrator
     */
    void createAdministratorAccount(AdministratorForRegistrationDto AdministratorForRegistrationDto);

    /**
     * Mapuje obiekt dto na obiekty modelu
     *
     * @param moderatorForRegistrationDto obiekt klasy dto która przechowuje wszystkie niezbędne pola do stworzenia nowego konta użytkownika z przypisanym poziomem dostępu Moderator
     */
    void createModeratorAccount(ModeratorForRegistrationDto moderatorForRegistrationDto);

    /**
     * Mapuje obiekt dto z nowym mailem do obiektu modelu oraz zmienia mail
     * @param accountChangeEmailDto dto z nowym mailem
     */
    void changeEmail(AccountChangeEmailDto accountChangeEmailDto);

    /**
     * Zmienia dane clienta o podanym loginie
     *
     * @param clientChangeDataDto dto obiekt przechowujący informację o kliencie oraz zmienionych danych
     */
    void changeClientData(ClientChangeDataDto clientChangeDataDto) throws OptimisticLockException, BaseAppException;

    /**
     * Zmienia dane pracownika o podanym loginie
     *
     * @param businessWorkerChangeDataDto dto obiekt przechowujący informację o procowniku oraz zmienionych danych
     */
    void changeBusinessWorkerData(BusinessWorkerChangeDataDto businessWorkerChangeDataDto) throws OptimisticLockException, BaseAppException;

    /**
     * Zmienia dane moderatora o podanym loginie
     *
     * @param moderatorChangeDataDto dto obiekt przechowujący informację o moderatorze oraz zmienionych danych
     */
    void changeModeratorData(ModeratorChangeDataDto moderatorChangeDataDto) throws OptimisticLockException, BaseAppException;

    /**
     * Zmienia dane administratora o podanym loginie
     *
     * @param administratorChangeDataDto dto obiekt przechowujący informację o administratorze oraz zmienionych danych
     */
    void changeAdministratorData(AdministratorChangeDataDto administratorChangeDataDto) throws OptimisticLockException, BaseAppException;

    /**
     * Zwraca konto o podanym loginie
     * @param login login
     * @return konto
     */
    AccountDto getAccountByLogin(String login) throws BaseAppException;

    /**
     * Pobira etag dla podanej encji
     * @param entity encja o interfejsie SignableEntity
     * @return etag
     *
     * @see SignableEntity
     */
    String getETagFromSignableEntity(SignableEntity entity);
}
