package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDtoForList;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.IdDto;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.GrantAccessLevelDto;
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
     * Pobiera obiekt AccountDto szukanego użytkownika
     *
     * @param login użytkownika
     * @return reprezentacja użytkownika po dokonanych zmianach w postaci AccountDto
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku nieznalezienia użytkownika.
     */
    AccountDto getAccountByLogin(String login) throws BaseAppException;

    /**
     * Dodaje poziom dostępu do użytkownika
     *
     * @param grantAccessLevel Obiekt przesyłowy danych potrzebnych do nadania poziomu dostępu
     * @return reprezentacja użytkownika po dokonanych zmianach w postaci AccountDto
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku gdy dodanie poziomu jest niemożliwe,
     *                          lub narusza zasady biznesowe aplikacji
     */
    AccountDto grantAccessLevel(GrantAccessLevelDto grantAccessLevel) throws BaseAppException;

    /**
     * Oblicza ETag dla encji
     *
     * @param entity Encja implementująca SignableEntity
     * @return ETag w postaci String
     * @throws BaseAppException Bazowy wyjątek aplikacji rzucany w przypadku błędu tworzonia ETaga
     */
    String getETagFromSignableEntity(SignableEntity entity) throws BaseAppException;


    /**
     * Mapuje obiety kont dto na obiekty modelu
     *
     * @return obiekty kont dto
     */
    List<AccountDtoForList> getAllAccounts();


    /**
     * Metoda odpowiedzialna za wywołanie metody odpowiedzialnej za blokowanie użytkownika
     * @param id ID użytkownika w postaci obiektu klasy IdDto
     */
    void blockUser(@Valid @NotNull IdDto id);


    /**
     * Metoda odpowiedzialna za wywołanie metody odpowiedzialnej za odblokowanie użytkownika
     * @param unblockedUserLogin login konta odblokowywanego
     * @param adminLogin login konta wykonującego odblokowanie konta
     * @param version wersja konta do weryfikacji
     * @throws BaseAppException Bazowy wyjątek aplikacji rzucany w przypadku błędu pobrania danych użytkownika
     */
    void unblockUser(@NotNull String unblockedUserLogin, @NotNull String adminLogin, @NotNull Long version) throws BaseAppException;
}
