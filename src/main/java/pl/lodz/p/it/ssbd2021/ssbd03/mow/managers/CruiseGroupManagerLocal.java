package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.*;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;

import javax.ejb.Local;
import java.util.List;
import java.util.UUID;

/**
 * Klasa która zarządza logiką biznesową grup wycieczek
 */
@Local
public interface CruiseGroupManagerLocal {
    /**
     * Tworzy nową grupę wycieczek
     *
     * @param companyName     nazwa firmy tworzącej grupę wycieczek
     * @param name            nazwa grupy wycieczek
     * @param number_of_seats ilośc miejsc na wycieczki z grupy wycieczek
     * @param price           cena bazowa wycieczek w grupie wycieczek.
     * @param start_address   adres startowy grupy wycieczek
     * @param pictures        lista zdjęć powiązanych z grupą wycieczek
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    void addCruiseGroup(String companyName, String name, long number_of_seats, Double price, CruiseAddress start_address, List<CruisePicture> pictures,String description) throws BaseAppException;

    /**
     * Edytuje daną grupę wycieczek
     *
     * @param name            nowa nazwa wycieczki
     * @param number_of_seats nowa ilość miejsc
     * @param price           nowa cena
     * @param start_address   nowy adres grupy wycieczek
     * @param version         wersja na potrzeby blokady optymistycznej
     * @return zmodyfikowany obiekt klasy CruiseGroup
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    void changeCruiseGroup(String name, long number_of_seats, Double price, CruiseAddress start_address, long version,String description, CruisePicture picture, UUID uuid) throws BaseAppException;


    /**
     * Pobiera wszystkie obiekty grup wycieczek z bazy
     *
     * @return wszystkie grupy wycieczek
     */
    List<CruiseGroup> getAllCruiseGroups() throws FacadeException;

    /**
     * Metoda zwracajaca aktywnego uzytkownika dla mow
     * @return Konto aktywnego uzytkownika
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
   Account getCurrentUser() throws BaseAppException;

        /**
         * Metoda odpowiedzialna za deaktywacje grupy wycieczek
         *
         * @param uuid uuid grupy wyceczek do deaktywacji oraz wersją
         * @param version wersjaq grupy wyceczek do deaktywacji
         * @throws BaseAppException Bazowy wyjątek aplikacji
         */
    void deactivateCruiseGroup(UUID uuid, long version) throws BaseAppException;

    /**
     * Pobiera wycieczki dla danej grupy wycieczek.
     * @param cruiseGroup grupa wycieczek
     * @return  lista wycieczek przypisanych do danej grupy wycieczek
     * @throws BaseAppException bazowy wyjątek aplikacji
     */
    List<Cruise> getCruiseBelongsToCruiseGroup(CruiseGroup cruiseGroup) throws BaseAppException;

    /**
     * Pobiera listę grup wycieczek należacych do danej firmy
     * @param companyName   nazwa firmy dla której mają byc pobrane wycieczki
     * @return  lista grup wycieczek dla danej firmy
     * @throws BaseAppException bazowy wyjątek aplikacji.
     */
    List<CruiseGroup> getCruiseGroupForBusinessWorker(String companyName) throws BaseAppException;

    /**
     * Pobiera grupe wycieczek o danym uuid
     *
     * @param uuid uuid grupy wycieczek
     * @return zwraca informacje o grupie wycieczek
     * @throws BaseAppException wyjątek wyrzucany w razie nie znależenia atrakcji
     */
    CruiseGroup findByUUID(UUID uuid) throws BaseAppException;
}
