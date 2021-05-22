package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;


import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.NewCruiseDto;

import javax.ejb.Local;
import java.util.UUID;

/**
 * Klasa która zarządza logiką biznesową wycieczek
 */

public interface CruiseManagerLocal {

    /**
     * Metoda zajmująca się tworzeniem nowego wycieczki
     *
     * @param cruise obiekt reprezentujący wycieczke
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia użytkownika tworzącego wycieczke
     */
    void addCruise(Cruise cruise) throws BaseAppException;


    /**
     * Metoda zajmująca się deaktywowaniem istniejącego wycieczke
     *
     * @param uuid uuid wycieczki
     * @param version wersja obiektu wycieczki wysyłana do sprawdzenia z wersją znajdującą się w bazie
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia wycieczki bądź konta deaktywującego
     */
    void deactivateCruise(UUID uuid, Long version) throws BaseAppException;

}
