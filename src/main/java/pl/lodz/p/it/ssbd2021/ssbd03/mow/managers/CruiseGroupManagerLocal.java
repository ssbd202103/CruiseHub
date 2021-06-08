package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseAddress;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruisePicture;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;

import javax.ejb.Local;
import java.util.List;

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
    CruiseGroup changeCruiseGroup(String name, long number_of_seats, Double price, CruiseAddress start_address, long version) throws BaseAppException;


    /**
     * Pobiera wszystkie obiekty grup wycieczek z bazy
     *
     * @return wszystkie grupy wycieczek
     */
    List<CruiseGroup> getAllCruiseGroups() throws FacadeException;


    /**
     * Deaktywuje daną grupę wycieczek
     *
     * @param name    nazwa grupy wycieczek
     * @param version wersja grupy wycieczek
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    CruiseGroup deactivateCruiseGroup(String name, Long version) throws BaseAppException;
}
