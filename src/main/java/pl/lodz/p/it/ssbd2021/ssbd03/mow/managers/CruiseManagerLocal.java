package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;


import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Klasa która zarządza logiką biznesową wycieczek
 */

public interface CruiseManagerLocal {

    /**
     * Zajmuje się tworzeniem nowego wycieczki
     *
     * @param cruise obiekt reprezentujący wycieczke
     * @param cruiseGroupUUID uuid grupy wycieczek
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia użytkownika tworzącego wycieczke, bądź grupy wycieczek
     */
    void addCruise(Cruise cruise, UUID cruiseGroupUUID) throws BaseAppException;


    /**
     * Zajmuje się deaktywowaniem istniejącego wycieczke
     *
     * @param uuid    uuid wycieczki
     * @param version wersja obiektu wycieczki wysyłana do sprawdzenia z wersją znajdującą się w bazie
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia wycieczki bądź konta deaktywującego, bądź złej wersji
     */
    void deactivateCruise(UUID uuid, Long version) throws BaseAppException;

    /**
     * Zwraca wycieczkę o podanym uuid
     *
     * @param uuid uuid wycieczki
     * @return obiekt encji reprezentujący wycieczkę
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia wycieczki
     */
    Cruise getCruise(UUID uuid) throws BaseAppException;

    /**
     * Zwraca wycieczki należących do grupy wycieczek o podanym uuid
     * @param uuid uuid grupy wycieczek
     * @return listę encji wycieczek nalężacych do grupy wycieczek o podanym uuid
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia wycieczki
     */
    List<Cruise> getCruisesByCruiseGroup(UUID uuid) throws BaseAppException;

    /**
     * Publikuje wycieczke
     *
     * @param cruiseVersion wersja encji wycieczki
     * @param cruiseUuid    uuid wycieczki
     * @return
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia wycieczki bądź konta publikującego
     */
    void publishCruise(long cruiseVersion, UUID cruiseUuid) throws BaseAppException;

    /**
     * Zajmuje się edycją wycieczki
     *
     * @param startDate   data rozpoczęcia wycieczki do zmiany
     * @param endDate     data zakończenia wycieczki do zmiany
     * @param uuid        uuid wycieczki
     * @param version     wersja obiektu wycieczki
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia wycieczki, bądź złej wersji
     */
    void editCruise(LocalDateTime startDate, LocalDateTime endDate, UUID uuid, Long version) throws BaseAppException;

    /**
     * Zwraca wszystkie opublikowane wycieczki
     *
     * @return Lista wycieczek
     */
    List<Cruise> getPublishedCruises();

    /**
     * Pobiera informacje o wycieczkach dla danej grupy wycieczek
     *
     * @param cruiseGroupName Nazwa grupy wycieczek
     * @return Lista wycieczek
     */
    List<Cruise> getCruisesForCruiseGroup(String cruiseGroupName);
}
