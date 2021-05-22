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
    List<Attraction> getAllAttraction() throws BaseAppException;

    /**
     * Metoda odpowiedzialna za usuwanie atrkacji
     *
     * @param name
     * @throws BaseAppException
     */
    void deleteAttraction(String name) throws BaseAppException;

    }
