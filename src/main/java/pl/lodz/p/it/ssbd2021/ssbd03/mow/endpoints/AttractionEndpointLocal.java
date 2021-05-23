package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.AddAttractionDto;

import javax.ejb.Local;

/**
 * Interfejs który zajmuje się gromadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z firmami oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Local
public interface AttractionEndpointLocal {

    //TODO Baza pod wypisanie wszytskich atrakcji
/*    *//**
     * Zwraca lista obiektów dto prezentujące informacje o fimach
     *
     * @return lista obiektów reprezentjace informacje o firmach
     *//*
    List<CompanyLightDto> getCompaniesInfo() throws BaseAppException;*/

    /**
     *  Metoda odpowiedzialna za wywołanie metody odpowiedzialnej za usunięcie atrkacji.
     *
     * @param name nazwa usuwanej atrakcji
     * @throws BaseAppException
     */
    void deleteAttraction(String name) throws BaseAppException;

    /**
     * Metoda odpowiedzialna za stworzenie atrakcji i dodanie jej do grupy wycieczek.
     * @param addAttractionDto Obiekt przesyłowy DTO reprezentujący atrakcję
     * @throws BaseAppException Wyjątek występujący w przypadku naruszenia zasad biznesowych.
     */
    void addAttraction(AddAttractionDto addAttractionDto) throws BaseAppException;
    }
