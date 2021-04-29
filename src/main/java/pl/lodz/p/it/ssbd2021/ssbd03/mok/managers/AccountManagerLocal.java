package pl.lodz.p.it.ssbd2021.ssbd03.mok.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Administrator;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Moderator;

import javax.ejb.Local;
import java.time.LocalDateTime;

/**
 * Klasa która zarządza logiką biznesową kont
 */
@Local
public interface AccountManagerLocal {
    /**
     * Tworzy konto przypisując do niego odpowiedni poziom dostępu oraz adres
     *
     * @param account obiekt prezentujący konto
     * @param client  obiekt prezentujący poziom dostępu klient
     * @param address obiekt prezentujący adres
     */
    void createClientAccount(Account account, Client client, Address address);

    /**
     * Tworzy konto przypisując do niego odpowiedni poziom dostępu
     *
     * @param account        obiekt prezentujący konto
     * @param businessWorker obiekt prezentujący poziom dostępu pracownik firmy
     */
    void createBusinessWorker(Account account, BusinessWorker businessWorker);

    /**
     * Tworzy konto przypisując do niego odpowiedni poziom dostępu
     *
     * @param account       obiekt prezentujący konto
     * @param administrator obiekt prezentujący poziom dostępu administrator
     */
    void createAdministrator(Account account, Administrator administrator);

    /**
     * Tworzy konto przypisując do niego odpowiedni poziom dostępu
     *
     * @param account   obiekt prezentujący konto
     * @param moderator obiekt prezentujący poziom dostępu administrator
     */
    void createModerator(Account account, Moderator moderator);

    /**
     * Metoda odpowiedzialna za edycję pól w bazie danych w przypadku niepoprawnego logowania.
     * @param login Login użytkownika
     * @param IpAddr Adres IP użytkownika
     * @param time Czas
     */
    void updateIncorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time);

    /**
     * Metoda odpowiedzialna za edycję pól w bazie danych w przypadku poprawnego logowania.
     * @param login Login użytkownika
     * @param IpAddr Adres IP użytkownika
     * @param time Czas
     * @return Token JWT
     */
    String updateCorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time);
}
