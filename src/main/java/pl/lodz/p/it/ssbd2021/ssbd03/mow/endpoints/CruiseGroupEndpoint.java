package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.CompanyMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.CompanyManagerLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.CruiseGroupManager;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Klasa która zajmuje się gromadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z firmami oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Stateful
public class CruiseGroupEndpoint implements CruiseGroupEndpointLocal {

    @EJB
    private CruiseGroupManager cruiseGroupManager;

    //TODO mozliwe wypisanie wszytchi grup wycieczek.
/*    @Override
    public List<> getCruiseGroupsInfo() {
        return cruiseGroupManager.getAllCruiseGroups(); //.stream().map(CompanyMapper::mapCompanyToCompanyLightDto).collect(Collectors.toList());
    }*/
}