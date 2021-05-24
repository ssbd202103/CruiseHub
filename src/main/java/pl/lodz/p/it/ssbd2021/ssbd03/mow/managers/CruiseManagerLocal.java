package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;


import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.NewCruiseDto;

import javax.ejb.Local;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Klasa która zarządza logiką biznesową wycieczek
 */

public interface CruiseManagerLocal {

    /**
     * Zajmuje się tworzeniem nowego wycieczki
     *
     * @param cruise obiekt reprezentujący wycieczke
     * @param cruiseGroupName nazwa grupy wycieczek
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia użytkownika tworzącego wycieczke, bądź grupy wycieczek
     */
    void addCruise(Cruise cruise, String cruiseGroupName) throws BaseAppException;


    /**
     * Zajmuje się deaktywowaniem istniejącego wycieczke
     *
     * @param uuid uuid wycieczki
     * @param version wersja obiektu wycieczki wysyłana do sprawdzenia z wersją znajdującą się w bazie
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia wycieczki bądź konta deaktywującego, bądź złej wersji
     */
    void deactivateCruise(UUID uuid, Long version) throws BaseAppException;


    /**
     * Zajmuje się edycją wycieczki
     *
     * @param description opis wycieczki do zmiany
     * @param startDate data rozpoczęcia wycieczki do zmiany
     * @param endDate data zakończenia wycieczki do zmiany
     * @param uuid uuid wycieczki
     * @param version wersja obiektu wycieczki
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia wycieczki, bądź złej wersji
     */
    void editCruise(String description, LocalDateTime startDate, LocalDateTime endDate, UUID uuid, Long version) throws BaseAppException;

}
