package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.addCruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.changeCruiseGroup.changeCruiseGroupDto;

import javax.ejb.Local;
/**
 * Interfejs który zajmuje się gromadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z grupą wycieczek, oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Local
public interface CruiseGroupEndpointLocal {
    /**
     * Tworzy nową grupę wycieczek  na podstawie dostarczonego dto
     * @param addCruiseGroupDto obiekt dto zawierający dane potrzene do utworzenia grupy wycieczek
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    void addCruiseGroup(addCruiseGroupDto addCruiseGroupDto) throws BaseAppException;

    /**
     * Umożliwia edycję grupy wycieczek
     * @param changeCruiseGroup obiekt dto zawierający dane potrzene do edycji grupy wycieczek
     * @return zmieniona grupa wycieczek
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    changeCruiseGroupDto changeCruiseGroup(changeCruiseGroupDto changeCruiseGroup)throws BaseAppException;
}
