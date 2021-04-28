package pl.lodz.p.it.ssbd2021.ssbd03.mok.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Administrator;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Moderator;


import javax.ejb.Local;


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
    void createBusinessWorker(Account account, BusinessWorker businessWorker, String companyName);

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
     * Zmienia email konta o podanym loginie
     *
     * @param login login konta
     * @param version wersja
     * @param newEmail nowy email
     */

    void changeEmail(String login, Long version, String newEmail);

    /**
     * Zmień dane klienta
     *
     * @param login login klienta
     * @param version wersja
     * @param newFirstName nowe imię
     * @param newSecondName nowe nazwisko
     * @param newPhoneNumber nowy numer telefonu
     * @param newHouseNumber nowy numer domu
     * @param newStreet nowa ulica
     * @param newPostalCode nowy kod pocztowy
     * @param newCity nowe miasto
     * @param newCountry nowe państwo
     */
    void changeClientData(String login, Long version,
                          String newFirstName, String newSecondName, String newPhoneNumber,
                          Long newHouseNumber, String newStreet, String newPostalCode, String newCity,String newCountry);

    /**
     * Zmień dane pracownika firmy
     *
     * @param login login pracownika
     * @param version wersja
     * @param newFirstName nowe imię
     * @param newSecondName nowe nazwisko
     * @param newPhoneNumber nowy numer telefonu
     */
    void changeBusinessWorkerData(String login, Long version, String newFirstName, String newSecondName, String newPhoneNumber);

    /**
     * Zmień dane moderatora
     *
     * @param login login moderatora
     * @param version wersja
     * @param newFirstName nowe imię
     * @param newSecondName nowe nazwisko
     *
     */
    void changeModeratorData(String login, Long version, String newFirstName, String newSecondName);

    /**
     * Zmień dane administratora
     *
     * @param login login administratora
     * @param version wersja
     * @param newFirstName nowe imię
     * @param newSecondName nowe nazwisko
     *
     */
    void changeAdministratorData(String login, Long version, String newFirstName, String newSecondName);
}
