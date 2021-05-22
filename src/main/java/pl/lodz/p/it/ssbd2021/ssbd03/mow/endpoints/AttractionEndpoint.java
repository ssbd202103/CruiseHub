package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.CompanyMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.AttractionManagerLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.CompanyManagerLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.CruiseGroupManagerLocal;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Klasa która zajmuje się gromadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z atrakcjami oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */

@Stateful
public class AttractionEndpoint implements AttractionEndpointLocal{

    @EJB
    private AttractionManagerLocal attractionManager;

//TODO baza pod wypisanie atrakcji
 /*   @Override
    public List<CompanyLightDto> getCompaniesInfo() throws BaseAppException {
        return companyManager.getAllCompanies().stream().map(CompanyMapper::mapCompanyToCompanyLightDto).collect(Collectors.toList());
    }*/

    @Override
    public void deleteAttraction(String name) throws BaseAppException {
        this.attractionManager.deleteAttraction(name);
    }
}