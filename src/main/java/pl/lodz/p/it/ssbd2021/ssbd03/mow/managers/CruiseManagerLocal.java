package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;


import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.NewCruiseDto;

import javax.ejb.Local;

/**
 * Klasa która zarządza logiką biznesową wycieczek (rejsów)
 */

public interface CruiseManagerLocal {

    /**
     * Metoda zajmująca się tworzeniem nowego rejsu
     *
     * @param cruise obiekt reprezentujący rejs
     * @throws BaseAppException Wyjątek rzucany w razie nie znalezienia użytkownika tworzącego rejs
     */
    void addCruise(Cruise cruise) throws BaseAppException;

}
