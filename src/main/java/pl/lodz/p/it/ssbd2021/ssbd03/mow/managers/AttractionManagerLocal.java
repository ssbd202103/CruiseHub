package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Attraction;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;

import javax.ejb.Local;
import java.util.List;

/**
 * Klasa która zarządza logiką biznesową atrkacji
 */
@Local
public interface AttractionManagerLocal {

    /**
     * Pobiera wszystkie obiekty firm z bazy
     *
     * @return the all companies
     */
    List<Attraction> getAllAttractions() throws BaseAppException;

    /**
     * Metoda odpowiedzialna za usuwanie atrkacji
     *
     * @param name Nazwa atrakcji
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    void deleteAttraction(String name) throws BaseAppException;


    /**
     * Metoda odpowiedzialna za stworzenie atrakcji i dodanie jej do grupy wycieczek.
     *
     * @param attraction Obiekt reprezentujący atrakcję
     * @throws BaseAppException Wyjątek występujący w przypadku naruszenia zasad biznesowych.
     */
    void addAttraction(Attraction attraction) throws BaseAppException;

    /**
     * Metoda odpowiedzialna za edycję istniejącej atrakcji.
     *
     * @param attraction Obiekt reprezentujący atrakcję po dokonanych zmianach.
     * @throws BaseAppException Wyjątek występujący w przypadku nieznalezienia atrakcji lub naruszenia zasad biznesowych.
     */
    void editAttraction(Attraction attraction) throws BaseAppException;
}
