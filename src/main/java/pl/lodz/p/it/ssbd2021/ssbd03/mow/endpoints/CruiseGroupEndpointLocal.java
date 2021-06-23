package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.common.dto.MetadataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints.TransactionalEndpoint;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups.CruiseGroupWithDetailsDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups.AddCruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups.ChangeCruiseGroupDto;

import javax.ejb.Local;
import java.util.List;
import java.util.UUID;

/**
 * Interfejs który zajmuje się gromadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z grupami wycieczek oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Local
public interface CruiseGroupEndpointLocal extends TransactionalEndpoint {
    /**
     * Tworzy nową grupę wycieczek  na podstawie dostarczonego dto
     *
     * @param addCruiseGroupDto obiekt dto zawierający dane potrzene do utworzenia grupy wycieczek
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    void addCruiseGroup(AddCruiseGroupDto addCruiseGroupDto) throws BaseAppException;

    /**
     * Umożliwia edycję grupy wycieczek
     *
     * @param changeCruiseGroup obiekt dto zawierający dane potrzene do edycji grupy wycieczek
     * @return zmieniona grupa wycieczek
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    void changeCruiseGroup(ChangeCruiseGroupDto changeCruiseGroup) throws BaseAppException;


    /**
     * Zwraca lista obiektów dto prezentujące informacje o grupach wycieczek
     *
     * @return lista obiektów reprezentjace informacje o grupach wycieczek
     */
    List<CruiseGroupWithDetailsDto> getCruiseGroupsInfo() throws BaseAppException;


    /**
     * Metoda odpowiedzialna za wywołanie metody odpowiedzialnej za deaktywacje grupy wycieczek
     *
     * @param uuid uuid grupy wyceczek do deaktywacji
     * @param version wersjaq grupy wyceczek do deaktywacji
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    void deactivateCruiseGroup(UUID uuid, Long version) throws BaseAppException;

    /**
     * Metoda odpowiedzialna za pobranie listy grup wycieczek dla podanej firmy
     * @param companyName nazwa firmy
     * @return  lista dto grup firm
     * @throws BaseAppException bazowy wyjątek aplikacji
     */
    List<CruiseGroupWithDetailsDto> getCruiseGroupForBusinessWorker(String companyName) throws BaseAppException;

    /**
     * Pobiera metadane grupy wycieczek
     *
     * @param uuid UUID grupy wycieczek wybranej do metadanych
     * @return Reprezentacja DTO metadanych
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    MetadataDto getCruiseGroupMetadata(UUID uuid) throws BaseAppException;
}
