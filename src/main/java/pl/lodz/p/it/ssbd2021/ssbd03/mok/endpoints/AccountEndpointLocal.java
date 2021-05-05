package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDtoForList;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.IdDto;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.AccountOwnPasswordDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.ChangeAccessLevelStateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.PasswordResetDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.GrantAccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccountDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;

import javax.ejb.Local;
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
     * Metoda odpowiedzialna za wywołanie metody odpowiedzialnej za wysyłania email z linkiem do resetowania hasła
     *
     * @param login login użytkonika
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku gdy użytkownik o podanym loginie nie istnieje, albo gdy podczas wysyłania email został rzucony wyjątek przez metode klasy EmailService
     */
    void requestPasswordReset(String login) throws BaseAppException;


    /**
     * Metoda odpowiedzialna za konwertacje dto na model aplikacji oraz za wywołanie metody odpowiedzialnej za resetowanie hasła użytkonwika.
     *
     * @param passwordResetDto obiekt dto reprezentujący dane niezbędne do resetowania hasła
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku gdy token wygasł albo nie przeszedł walidacji oraz gdy login który znajduje się w tokenie jest rózny od przesłanego jawnie w dto oraz w sytuacj gdy został rzucony wyjątek blokady optymistycznej
     */
    void resetPassword(PasswordResetDto passwordResetDto) throws BaseAppException;


    /**
     * Metoda odpowiedzialna za wywołanie metody odpowiedzialnej za odblokowanie użytkownika
     * @param unblockedUserLogin login konta odblokowywanego
     * @param version wersja konta do weryfikacji
     * @throws BaseAppException Bazowy wyjątek aplikacji rzucany w przypadku błędu pobrania danych użytkownika
     */
    void unblockUser(@NotNull String unblockedUserLogin, @NotNull Long version) throws BaseAppException;

    /**
     * Metoda odpowiedzialna za wywołanie metody odpowiedzialnej za zmianę hasła akutalnego użytkownika
     * @param accountOwnPasswordDto obiekt dto posiadający niezbedne dane do zmienienia hasła aktualnego użytkownika
     * @throws BaseAppException Bazowy wyjątek aplikacji rzucany w przypadku gdy stare hasło podane przez użytkownika nie jest zgodne z tym w bazie danych, bądź w przpadku blokady optymistycznej
     */
    void changeOwnPassword(AccountOwnPasswordDto accountOwnPasswordDto) throws BaseAppException;
}
