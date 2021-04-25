package pl.lodz.p.it.ssbd2021.ssbd03.mok.managers;

import javassist.compiler.ast.Pair;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Administrator;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Moderator;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AuthenticateResponse;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

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
     * Loguje użytkwnika lub zwraca odpowiedni kod błędu
     * @param login Login użytkownika
     * @param passwordHash Hash hasła użytkownika
     * @return Kod HTTP, konto użytkownika oraz token
     */
    AuthenticateResponse authenticate(@NotNull String login, @NotNull String passwordHash);
}
