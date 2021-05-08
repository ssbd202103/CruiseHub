package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDtoForList;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.IdDto;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.OtherAccountChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.OtherBusinessWorkerChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.OtherClientChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.ChangeAccessLevelStateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.GrantAccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccountDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;

import javax.ejb.Local;
import javax.persistence.OptimisticLockException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * Interfejs który zajmuje się gromadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z kontami użytkowników i poziomami dostępu, oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Local
public interface AccountEndpointLocal {
    /**
     * Mapuje obiekt dto na obiekty modelu
     *
     * @param clientForRegistrationDto Obiekt klasy dto która przechowuje wszystkie niezbędne pola do stworzenia nowego konta użytkownika z przypisanym poziomem dostępu Klient
     */
    void createClientAccount(ClientForRegistrationDto clientForRegistrationDto);

    /**
     * Mapuje obiekt dto na obiekty modelu
     *
     * @param businessWorkerForRegistrationDto Obiekt klasy dto która przechowuje wszystkie niezbędne pola do stworzenia nowego konta użytkownika z przypisanym poziomem dostępu Pracownik Firmy
     */
    void createBusinessWorkerAccount(BusinessWorkerForRegistrationDto businessWorkerForRegistrationDto);

    /**
     * Pobiera obiekt AccountDto szukanego użytkownika
     *
     * @param login Login użytkownika
     * @return Reprezentacja użytkownika po dokonanych zmianach w postaci AccountDto
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku nieznalezienia użytkownika.
     */
    AccountDto getAccountByLogin(String login) throws BaseAppException;

    /**
     * Pobiera obiekt AccountDetailsViewDto szukanego użytkownika
     *
     * @param login Login użytkownika
     * @return Reprezentacja użytkownika po dokonanych zmianach w postaci AccountDetailsViewDto
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku nieznalezienia użytkownika.
     */
    AccountDetailsViewDto getAccountDetailsByLogin(String login) throws BaseAppException;

    /**
     * Dodaje poziom dostępu do użytkownika
     *
     * @param grantAccessLevel Obiekt przesyłowy danych potrzebnych do nadania poziomu dostępu
     * @return Reprezentacja użytkownika po dokonanych zmianach w postaci AccountDto
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku gdy dodanie poziomu jest niemożliwe,
     *                          lub narusza zasady biznesowe aplikacji
     */
    AccountDto grantAccessLevel(GrantAccessLevelDto grantAccessLevel) throws BaseAppException;

    /**
     * Zmienia stan poziomu dostępu użytkownika (włącza/wyłącza)
     *
     * @param changeAccessLevelState Obiekt przesyłowy danych potrzebnych do zmiany stanu poziomu dostępu
     * @return Reprezentacja użytkownika po dokonanych zmianach w postaci AccountDto
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku gdy zmiana stanu poziomu dostepu jest niemożliwa,
     *                          lub narusza zasady biznesowe aplikacji
     */
    AccountDto changeAccessLevelState(ChangeAccessLevelStateDto changeAccessLevelState) throws BaseAppException;

    /**
     * Oblicza ETag dla encji
     *
     * @param entity Encja implementująca SignableEntity
     * @return ETag w postaci String
     */
    String getETagFromSignableEntity(SignableEntity entity);


    /**
     * Pobiera wszystkie konta w postaci obiektów przesyłowych DTO
     *
     * @return Obiekty kont dto
     */
    List<AccountDtoForList> getAllAccounts();


    /**
     * Metoda odpowiedzialna za wywołanie metody odpowiedzialnej za blokowanie użytkownika
     *
     * @param id ID użytkownika w postaci obiektu klasy IdDto
     */
    void blockUser(@Valid @NotNull IdDto id);


    /**
     * Zmienia dane wybranego klienta
     *
     * @param otherClientChangeDataDto dto obiekt przechowujący informację o zmienionych danych
     */
    OtherClientChangeDataDto changeOtherClientData(OtherClientChangeDataDto otherClientChangeDataDto) throws OptimisticLockException, BaseAppException;

    /**
     * Zmienia dane wybranego pracownika
     *
     * @param otherBusinessWorkerChangeDataDto dto obiekt przechowujący informację o  zmienionych danych
     */
    OtherBusinessWorkerChangeDataDto changeOtherBusinessWorkerData(OtherBusinessWorkerChangeDataDto otherBusinessWorkerChangeDataDto) throws OptimisticLockException, BaseAppException;

    /**
     * Zmienia dane wybranego moderatora lub administratora
     *
     * @param otherAccountChangeDataDto dto obiekt przechowujący informację o zmienionych danych
     */
    AccountDto changeOtherAccountData(OtherAccountChangeDataDto otherAccountChangeDataDto) throws OptimisticLockException, BaseAppException;

}
