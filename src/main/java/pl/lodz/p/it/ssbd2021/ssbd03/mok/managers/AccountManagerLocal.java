package pl.lodz.p.it.ssbd2021.ssbd03.mok.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Moderator;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;

import javax.ejb.Local;
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
    void createClientAccount(Account account, Client client);

    /**
     * Tworzy konto przypisując do niego odpowiedni poziom dostępu
     *
     * @param account        obiekt prezentujący konto
     * @param businessWorker obiekt prezentujący poziom dostępu pracownik firmy
     */
    void createBusinessWorkerAccount(Account account, BusinessWorker businessWorker, String companyName);

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
     * Metoda odpowiedzialna za blokowanie konta
     * @param id ID użytkownika
     */
    void blockUser(long id);


    /**
     * Metoda odpowiedzialna za odblokowanie konta
     * @param unblockedUserLogin login konta odblokowywanego
     * @param version wersja obiektu do sprawdzenia
     * @throws BaseAppException Bazowy wyjątek aplikacji, zwracany w przypadku gdy występuje błąd w p
     *                          obraniu danych w fasadzie
     */
    void unblockUser(String unblockedUserLogin, Long version) throws BaseAppException;


    /**
     * Pobiera liste kont z bazy danych
     *
     * @return lista kont
     */
    List<Account> getAllAccounts();
}
