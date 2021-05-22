package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.DeactivateCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.NewCruiseDto;

import javax.ejb.Local;
import java.util.UUID;


/**
 * Interfejs który zajmuje się obsługą obiektów dto z zakresu wycieczek
 */
@Local
public interface CruiseEndpointLocal {

    /**
     * Tworzenie nowego rejsu
     *
     * @param newCruiseDto obiekt reprezentujący wycieczki
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia konta osoby tworzącej wycieczke
     */
    void addCruise(NewCruiseDto newCruiseDto) throws BaseAppException;


    /**
     * Deaktywowanie wycieczki
     * @param deactivateCruiseDto obiekt dto posiadający uuid oraz wersję wycieczki
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia wycieczki bądź konta deaktywującego
     */
    void deactivateCruise(DeactivateCruiseDto deactivateCruiseDto) throws BaseAppException;

}
