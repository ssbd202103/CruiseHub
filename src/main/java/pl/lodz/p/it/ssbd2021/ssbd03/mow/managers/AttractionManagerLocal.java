package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Attraction;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;

import javax.annotation.security.PermitAll;
import javax.ejb.Local;
import java.util.List;
import java.util.UUID;

/**
 * Klasa która zarządza logiką biznesową atrkacji
 */
@Local
public interface AttractionManagerLocal {


    /**
     * Metoda odpowiedzialna za usuwanie atrkacji
     *
     * @param uuid UUID atrakcji
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    void deleteAttraction(UUID uuid) throws BaseAppException;


    /**
     * Metoda odpowiedzialna za stworzenie atrakcji i dodanie jej do grupy wycieczek.
     *
     * @param attraction Obiekt reprezentujący atrakcję
     * @param cruiseUUID UUID wycieczki, dla której ma być utworzona atrakcja
     * @return UUID nowo utworzonej atrakcji
     * @throws BaseAppException Wyjątek występujący w przypadku naruszenia zasad biznesowych.
     */
    UUID addAttraction(Attraction attraction, UUID cruiseUUID) throws BaseAppException;


    /**
     * Metoda odpowiedzialna za edycję istniejącej atrakcji.
     *
     * @param attractionUUID   UUID atrakcji
     * @param newName          Nowa nazwa atrakcji
     * @param newDescription   Nowy opis atrakcji
     * @param newPrice         Nowa cena atrakcji
     * @param newNumberOfSeats Nowa liczba miejsc atrakcji
     * @param version          Wersja przekazywanej atrakcji
     * @throws BaseAppException Wyjątek występujący w przypadku nieznalezienia atrakcji lub naruszenia zasad biznesowych.
     */
    void editAttraction(UUID attractionUUID, String newName, String newDescription, double newPrice, int newNumberOfSeats, long version) throws BaseAppException;

    /**
     * Pobiera atrakcje wycieczki o danym uuid
     *
     * @param uuid uuid wycieczki
     * @throws BaseAppException wyjątek wyrzucany w razie nie znależenia atrakcji
     */
    List<Attraction> findByCruiseUUID(UUID uuid) throws BaseAppException;

    /**
     * Pobiera atrakcje o danym uuid
     *
     * @param uuid uuid atrakcji
     * @return zwraca informacje o atrakcje
     * @throws BaseAppException wyjątek wyrzucany w razie nie znależenia atrakcji
     */
    Attraction findByUUID(UUID uuid) throws BaseAppException;


}
