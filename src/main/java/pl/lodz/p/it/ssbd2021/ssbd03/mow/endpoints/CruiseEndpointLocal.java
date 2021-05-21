package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.NewCruiseDto;

import javax.ejb.Local;


/**
 * Interfejs który zajmuje się obsługą obiektów dto z zakresu wycieczek (rejsów)
 */
@Local
public interface CruiseEndpointLocal {

    /**
     * Tworzenie nowego rejsu
     *
     * @param newCruiseDto Obiekt reprezentujący rejs
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia konta osoby tworzącej rejs
     */
    void addCruise(NewCruiseDto newCruiseDto) throws BaseAppException;
}
