package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.changeCruiseGroup.CompanyDto;

import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs który zajmuje się gromadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z firmami oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Local
public interface CompanyEndpointLocal {
    /**
     * Zwraca liste obiektów dto prezentujące informacje o fimach niezbędne dla utworzenia użytkownika z poziomem dostępu BusinessWorker
     *
     * @return lista obiektów reprezentjace informacje o fimach niezbędne dla utworzenia użytkownika z poziomem dostępu BusinessWorker
     */
    List<CompanyLightDto> getCompaniesInfo() throws BaseAppException;

    /**
     * Zwraca lista obiektów dto prezentujące informacje o fimach
     *
     * @return lista obiektów dto prezentujące informacje o fimach
     */
    List<CompanyDto> getAllCompanies(String userLogin) throws BaseAppException;
}
