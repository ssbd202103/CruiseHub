package pl.lodz.p.it.ssbd2021.ssbd03.mok.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.AuthUnauthorizedException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;

import javax.ejb.Local;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Klasa która zarządza logiką biznesową kont
 */
@Local
public interface AccountManagerLocal {

    /**
     * Pobiera z bazy danych obiekt szukanego użytkownika
     *
     * @param login użytkownika
     * @return obiekt encji użytkownika
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku nieznalezienia użytkownika.
     */
    Account getAccountByLogin(String login) throws BaseAppException;

    /**
     * Tworzy konto przypisując do niego odpowiedni poziom dostępu oraz adres
     *
     * @param account obiekt prezentujący konto
     * @param client  obiekt prezentujący poziom dostępu klient
     */
    void createClientAccount(Account account, Client client) throws BaseAppException;

    /**
     * Tworzy konto przypisując do niego odpowiedni poziom dostępu
     *
     * @param account        obiekt prezentujący konto
     * @param businessWorker obiekt prezentujący poziom dostępu pracownik firmy
     */
    void createBusinessWorkerAccount(Account account, BusinessWorker businessWorker, String companyName) throws BaseAppException;

    /**
     * Dodaje poziom dostępu administratora użytkownikowi
     *
     * @param accountLogin   login użytkownika
     * @param accountVersion wersja konta w momencie wykonywania zmian
     * @return obiekt encji użytkownika po dokonanych zmianach
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku gdy dodanie poziomu jest niemożliwe,
     *                          lub narusza zasady biznesowe aplikacji
     */
    Account grantModeratorAccessLevel(String accountLogin, Long accountVersion) throws BaseAppException;

    /**
     * Dodaje poziom dostępu administratora użytkownikowi
     *
     * @param accountLogin   login użytkownika
     * @param accountVersion wersja konta w momencie wykonywania zmian
     * @return obiekt encji użytkownika po dokonanych zmianach
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku gdy dodanie poziomu jest niemożliwe,
     *                          lub narusza zasady biznesowe aplikacji
     */
    Account grantAdministratorAccessLevel(String accountLogin, Long accountVersion) throws BaseAppException;


    /**
     * Zmienia stan poziomu dostępu użytkownika (włącza/wyłącza)
     * @param accountLogin Login użytkownika
     * @param accessLevel Poziom dostępu użytkownika
     * @param enabled Boolean określający oczekiwany stan poziomu dostępu
     * @param accountVersion Wersja obiektu przed wywołaniem metody
     * @return Obiekt użytkownika po dokonanych zmianach
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku gdy zmiana stanu poziomu dostepu jest niemożliwa,
     *                          lub narusza zasady biznesowe aplikacji
     */
    Account changeAccessLevelState(String accountLogin, AccessLevelType accessLevel,
                                   boolean enabled, Long accountVersion) throws BaseAppException;

    /**
     * Metoda odpowiedzialna za blokowanie konta
     *
     * Blokuje użytkownika o zadanym loginie
     * @param login Login użytkownika
     * @param version Wersja obiektu do sprawdzenia
     * @throws BaseAppException Wyjątek aplikacji rzucany w przypadku błędu pobrania danych użytkownika
     */
    Account blockUser(String login, Long version) throws BaseAppException;


    /**
     * Metoda odpowiedzialna za odblokowanie konta
     * @param unblockedUserLogin login konta odblokowywanego
     * @param version wersja obiektu do sprawdzenia
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku gdy występuje błąd w p
     *                          obraniu danych w fasadzie
     */
    Account unblockUser(String unblockedUserLogin, Long version) throws BaseAppException;

    /**
     * Pobiera liste kont z bazy danych
     *
     * @return lista kont
     */
    List<Account> getAllAccounts();


    /**
     * Metoda odpowiedzialna za wysyłania email z linkiem do resetowania hasła
     *
     * @param login login użytkonika
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku gdy użytkownik o podanym loginie nie istnieje, albo gdy podczas wysyłania email został rzucony wyjątek przez metode klasy EmailService
     */
    void requestPasswordReset(String login) throws BaseAppException;

    /**
     * Metoda odpowiedzialna za resetowanie hasła użytkonwika.
     *
     * @param login        login użytkownika
     * @param passwordHash zachaszowane hasło
     * @param token        jwt token otrzymany przez email
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku gdy token wygasł albo nie przeszedł walidacji oraz gdy login który znajduje się w tokenie jest rózny od przesłanego jawnie w dto oraz w sytuacj gdy został rzucony wyjątek blokady optymistycznej
     */
    void resetPassword(String login, String passwordHash, String token) throws BaseAppException;

    /**
     * Metoda odpowiedzialna za wysyłania email z linkiem do resetowania hasła na podany email
     *
     * @param login login użytkonika
     * @param email email użytkonika
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku gdy użytkownik o podanym loginie nie istnieje, albo gdy podczas wysyłania email został rzucony wyjątek przez metode klasy EmailService
     */
    void requestSomeonesPasswordReset(String login, String email) throws BaseAppException;

    /**
     * Metoda odpowiedzialna za weryfikacje konta użytkonwika.
     *
     * @param token        jwt token otrzymany przez email
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku gdy token wygasł albo nie przeszedł walidacji oraz gdy brak loginu lub wersji w tokenie oraz w wypadku kiedy konto zostało już wcześniej aktywowane oraz w sytuacj gdy został rzucony wyjątek blokady optymistycznej
     */
    void verifyAccount(String token) throws BaseAppException;


    /**
     * Zmienia dane wybranego klienta
     * @param login login klienta
     * @param phoneNumber zmieniony numer telefonu
     * @param addr zmieniony adres
     * @paramm version wersja
     * @return zmienione konto
     * @throws BaseAppException  Bazowy wyjątek aplikacji, zwracany w przypadku gdy token wygasł albo nie przeszedł walidacji oraz gdy brak loginu lub wersji w tokenie oraz w wypadku kiedy konto zostało już wcześniej aktywowane oraz w sytuacj gdy został rzucony wyjątek blokady optymistycznej
     */
    Account changeOtherClientData(String login, String phoneNumber, Address addr,Long version) throws BaseAppException;

    /**
     * Zmienia dane wybranego pracownika firmy
     * @param login login pracownika
     * @param phoneNumber nowy numer telefonu
     * @param version wersja
     * @return zmienione konto
     * @throws BaseAppException  Bazowy wyjątek aplikacji, zwracany w przypadku gdy token wygasł albo nie przeszedł walidacji oraz gdy brak loginu lub wersji w tokenie oraz w wypadku kiedy konto zostało już wcześniej aktywowane oraz w sytuacj gdy został rzucony wyjątek blokady optymistycznej
     */
    Account changeOtherBusinessWorkerData(String login, String phoneNumber,Long version) throws BaseAppException;

    /**
     * Zmień dane wybranego moderatora lub administratora
     *
     * @param account encja konta zawierająca zmiany
     * @return zmienone konto
     */
    Account changeOtherAccountData(Account account) throws BaseAppException;


    /**
     * Zmienia email konta o podanym loginie
     *
     * @param login login konta
     * @param version wersja
     * @param newEmail nowy email
     */

    void changeEmail(String login, Long version, String newEmail) throws BaseAppException;

    /**
     * Zmień dane klienta
     *
     * @param account encja konta zawierająca zmiany
     *
     */
    void changeClientData(Account account) throws BaseAppException;

    /**
     * Zmień dane pracownika firmy
     *
     * @param account encja konta zawierająca zmiany
     *
     */
    void changeBusinessWorkerData(Account account) throws BaseAppException;

    /**
     * Zmień dane moderatora
     *
     * @param account encja konta zawierająca zmiany
     *
     */
    void changeModeratorData(Account account) throws BaseAppException;

    /**
     * Zmień dane administratora
     *
     * @param account encja konta zawierająca zmiany
     *
     */
    void changeAdministratorData(Account account) throws BaseAppException;

    /**
     * Metoda odpowiedzialna za edycję pól w bazie danych w przypadku niepoprawnego logowania.
     * @param login Login użytkownika
     * @param IpAddr Adres IP użytkownika
     * @param time Czas
     */
    void updateIncorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time) throws AuthUnauthorizedException;

    /**
     * Metoda odpowiedzialna za edycję pól w bazie danych w przypadku poprawnego logowania.
     * @param login Login użytkownika
     * @param IpAddr Adres IP użytkownika
     * @param time Czas
     * @return Token JWT
     */
    String updateCorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time) throws AuthUnauthorizedException;

	/**
     * Metoda odpowiedzialna za zmiane hasła akutalnego użytkownika
     * @throws BaseAppException Bazowy wyjątek aplikacji rzucany w przypadku gdy stare hasło nie jest zgodne z tym z bazy danych
     */
    void changeOwnPassword(String login, Long version, String oldPassword, String newPassword) throws BaseAppException;
}
