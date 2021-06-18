package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.AddAttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.AttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.EditAttractionDto;

import javax.ejb.Local;
import java.util.List;
import java.util.UUID;

/**
 * Interfejs który zajmuje się gromadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z firmami oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Local
public interface AttractionEndpointLocal {

    /**
     * Metoda odpowiedzialna za wywołanie metody odpowiedzialnej za usunięcie atrkacji.
     *
     * @param id UUID usuwanej atrakcji
     * @throws BaseAppException
     */
    void deleteAttraction(long id) throws BaseAppException;

    /**
     * Metoda odpowiedzialna za stworzenie atrakcji i dodanie jej do grupy wycieczek.
     *
     * @param addAttractionDto Obiekt przesyłowy DTO reprezentujący atrakcję
     * @return UUID nowo utworzonej atrakcji.
     * @throws BaseAppException Wyjątek występujący w przypadku naruszenia zasad biznesowych.
     */
    UUID addAttraction(AddAttractionDto addAttractionDto) throws BaseAppException;

    /**
     * Metoda odpowiedzialna za edycję istniejącej atrakcji
     *
     * @param editAttractionDto Obiekt przesyłowy DTO reprezentujący atrakcję po zmianach
     * @throws BaseAppException Wyjątek występujący w przypadku nieznalezienia atrakcji lub naruszenia zasad biznesowych.
     */
    void editAttraction(EditAttractionDto editAttractionDto) throws BaseAppException;

    List<AttractionDto> getAttractionsByCruiseUUID(UUID uuid) throws BaseAppException;
}
