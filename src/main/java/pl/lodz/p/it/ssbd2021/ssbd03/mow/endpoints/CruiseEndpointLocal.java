package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.common.dto.MetadataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints.TransactionalEndpoint;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.*;

import javax.ejb.Local;
import java.util.List;
import java.util.UUID;


/**
 * Interfejs który zajmuje się obsługą obiektów dto z zakresu wycieczek
 */
@Local
public interface CruiseEndpointLocal extends TransactionalEndpoint {

    /**
     * Tworzenie nowego rejsu
     *
     * @param newCruiseDto obiekt reprezentujący wycieczki
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia konta osoby tworzącej wycieczke, bądź grupy wycieczek
     */
    void addCruise(NewCruiseDto newCruiseDto) throws BaseAppException;


    /**
     * Deaktywowanie wycieczki
     *
     * @param deactivateCruiseDto obiekt dto posiadający uuid oraz wersję wycieczki
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia wycieczki bądź konta deaktywującego, bądź złej wersji
     */
    void deactivateCruise(DeactivateCruiseDto deactivateCruiseDto) throws BaseAppException;

    /**
     * Otrzymanie informacji o wycieczce o podanym uuid
     *
     * @param uuid uuid wycieczki
     * @return obiekt dto przechowujący informację o wycieczce
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia wycieczki bądź konta deaktywującego
     */
    CruiseDto getCruise(UUID uuid) throws BaseAppException;

    /**
     * Zwraca wycieczki należących do grupy wycieczek o podanym uuid
     * @param uuid uuid grupy wycieczek
     * @return listę encji wycieczek nalężacych do grupy wycieczek o podanym uuid
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia wycieczki
     */
    List<RelatedCruiseDto> getCruisesByCruiseGroup(UUID uuid) throws BaseAppException;

    /**
     * Publikuje wycieczke
     *
     * @param publishCruiseDto dto niezbędne do publikacji wycieczki
     * @return
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia wycieczki bądź konta publikującego
     */
    void publishCruise(PublishCruiseDto publishCruiseDto) throws BaseAppException;

    /**
     * Edycja wycieczki
     *
     * @param editCruiseDto obiekt dto posiadający zmiany w wycieczce, versje oraz UUID
     * @throws BaseAppException wyjątek rzucany w razie nie znalezienia wycieczki bądź konta deaktywującego, bądź złej wersji
     */
    void editCruise(EditCruiseDto editCruiseDto) throws BaseAppException;

    /**
     * Zwraca wszystkie opublikowane wycieczki
     *
     * @return Lista wycieczek
     * @throws BaseAppException Bazowy wyjatek aplikacji
     */
    List<CruiseGroupWithCruisesDto> getPublishedCruises() throws BaseAppException;

    /**
     * Pobiera informacje o wycieczkach dla danej grupy wycieczek
     *
     * @param cruiseGroupUUID UUID grupy wycieczek
     * @return Lista wycieczek w reprezentacji DTO
     */
    List<CruiseForCruiseGroupDto> getCruisesForCruiseGroup(UUID cruiseGroupUUID) throws BaseAppException;

    /**
     * Pobiera metadane wycieczki
     *
     * @param uuid UUID wycieczki wybranej do metadanych
     * @return Reprezentacja DTO metadanych
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    MetadataDto getCruiseMetadata(UUID uuid) throws BaseAppException;
}
