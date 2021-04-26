package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CompanyLightDto;

import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs który zajmuje się gromadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z firmami oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Local
public interface CompanyEndpointLocal {
    /**
     * Zwraca lista obiektów dto prezentujące informacje o fimach
     *
     * @return lista obiektów reprezentjace informacje o firmach
     */
    List<CompanyLightDto> getCompaniesInfo();
}
