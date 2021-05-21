package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;


import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.CruiseGroupManager;


import javax.ejb.EJB;
import javax.ejb.Stateful;


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

    @Override
    public void deactivateCruiseGroup(String name, Long version) throws BaseAppException {
       this.cruiseGroupManager.deactivateCruiseGroup(name, version);
    }
}