package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;


import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;

import java.util.UUID;

/**
 * Klasa która zarządza logiką biznesową wycieczek
 */

public interface CruiseManagerLocal {

    /**
     * Zajmuje się tworzeniem nowego wycieczki
     *
     * @param cruise          obiekt reprezentujący wycieczke
     * @param cruiseGroupName nazwa grupy wycieczek
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia użytkownika tworzącego wycieczke, bądź grupy wycieczek
     */
    void addCruise(Cruise cruise, String cruiseGroupName) throws BaseAppException;


    /**
     * Zajmuje się deaktywowaniem istniejącego wycieczke
     *
     * @param uuid    uuid wycieczki
     * @param version wersja obiektu wycieczki wysyłana do sprawdzenia z wersją znajdującą się w bazie
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia wycieczki bądź konta deaktywującego
     */
    void deactivateCruise(UUID uuid, Long version) throws BaseAppException;

    /**
     * Zwraca wycieczkę o podanym uuid
     *
     * @param uuid uuid wycieczki
     * @return obiekt encji reprezentujący wycieczkę
     * @throws BaseAppException wyjątek rzucany w raze nie znalezienia wycieczki
     */
    Cruise getCruise(UUID uuid) throws BaseAppException;


    /**
     * Publikuje wycieczke
     *
     * @param cruiseVersion wersja encji wycieczki
     * @param cruiseUuid    uuid wycieczki
     * @return
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia wycieczki bądź konta publikującego
     */
    void publishCruise(long cruiseVersion, UUID cruiseUuid) throws BaseAppException;
}
