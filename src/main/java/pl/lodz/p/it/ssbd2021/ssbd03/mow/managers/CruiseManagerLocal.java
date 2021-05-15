package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;


import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.NewCruiseDto;

import javax.ejb.Local;

/**
 * Klasa która zarządza logiką biznesową wycieczek
 */
@Local
public interface CruiseManagerLocal {

    void addCruise(Cruise cruise) throws BaseAppException;

}
