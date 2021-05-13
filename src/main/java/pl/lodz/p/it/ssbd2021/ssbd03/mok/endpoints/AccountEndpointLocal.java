package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDtoForList;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.*;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.ChangeAccessLevelStateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.OtherAccountChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.OtherBusinessWorkerChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.OtherClientChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.GrantAccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccountDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.AccountChangeEmailDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.AdministratorChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.BusinessWorkerChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.ClientChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.ModeratorChangeDataDto;
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
    void createClientAccount(ClientForRegistrationDto clientForRegistrationDto) throws BaseAppException;

    /**
     * Mapuje obiekt dto na obiekty modelu
     *
     * @param businessWorkerForRegistrationDto Obiekt klasy dto która przechowuje wszystkie niezbędne pola do stworzenia nowego konta użytkownika z przypisanym poziomem dostępu Pracownik Firmy
     */
    void createBusinessWorkerAccount(BusinessWorkerForRegistrationDto businessWorkerForRegistrationDto) throws BaseAppException;



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
     * Pobiera wszystkie konta w postaci obiektów przesyłowych DTO
     *
     * @return Obiekty kont dto
     */
    List<AccountDtoForList> getAllAccounts();


    /**
     * Metoda odpowiedzialna za wywołanie metody odpowiedzialnej za blokowanie użytkownika
     * @param login Login blokowanego użytkownika
     * @param version wersja konta do weryfikacji
     * @throws BaseAppException Wyjątek aplikacji rzucany w przypadku błędu pobrania danych użytkownika
     */
    void blockUser(@NotNull String login, @NotNull Long version) throws BaseAppException;



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
     * Metoda odpowiedzialna za wywołanie metody odpowiedzialnej za wysyłania email z linkiem do resetowania hasła dla danego użytkownika, który podał wybrany email
     *
     * @param login login użytkonika
     * @param email email użytkonika
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku gdy użytkownik o podanym loginie nie istnieje, albo gdy podczas wysyłania email został rzucony wyjątek przez metode klasy EmailService
     */
    void requestSomeonesPasswordReset(String login, String email) throws BaseAppException;

    void verifyAccount(AccountVerificationDto accountVerificationDto) throws BaseAppException;
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
    /**
     * Mapuje obiekt dto z nowym mailem do obiektu modelu oraz zmienia mail
     * @param accountChangeEmailDto dto z nowym mailem
     */
    void changeEmail(AccountChangeEmailDto accountChangeEmailDto) throws BaseAppException, OptimisticLockException;

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
     * Zwraca dto konta clienta o podanym loginie
     * @param login login
     * @return dto konta clienta
     * @throws BaseAppException
     */
    ClientDto getClientByLogin(String login) throws BaseAppException;

    /**
     * Zwraca dto konta pracownika firmy o podanym loginie
     * @param login login
     * @return dto konta pracownika firmy
     * @throws BaseAppException
     */
    BusinessWorkerDto getBusinessWorkerByLogin(String login) throws BaseAppException;

    /**
     * Zwraca dto konta moderatora o podanym loginie
     * @param login login
     * @return dto konta moderatora
     * @throws BaseAppException
     */
    ModeratorDto getModeratorByLogin(String login) throws BaseAppException;

    /**
     * Zwraca dto konta administratora o podanym loginie
     * @param login login
     * @return dto konta administratora
     * @throws BaseAppException
     */
    AdministratorDto getAdministratorByLogin(String login) throws BaseAppException;

    /**
     * Pobira etag dla podanej encji
     * @param entity encja o interfejsie SignableEntity
     * @return etag
     *
     * @see SignableEntity
     */
    String getETagFromSignableEntity(SignableEntity entity);
}

