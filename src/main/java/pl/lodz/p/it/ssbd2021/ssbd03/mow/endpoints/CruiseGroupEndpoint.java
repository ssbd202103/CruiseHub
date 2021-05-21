package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;


import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseAddress;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruisePicture;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.addCruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.changeCruiseGroup.changeCruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.CruiseGroupMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.CruiseGroupManagerLocal;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;

/**
 * Klasa która zajmuje się gromadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z firmami oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Stateful
public class CruiseGroupEndpoint implements CruiseGroupEndpointLocal {

    @EJB
    private CruiseGroupManagerLocal cruiseGroupManager;

    @Override
    public void addCruiseGroup(addCruiseGroupDto addCruiseGroupDto) throws BaseAppException{
        CruiseAddress cruiseAddress = CruiseGroupMapper.extractAddressForAddingCruiseGroup(addCruiseGroupDto);
        List<CruisePicture> cruisePicture = CruiseGroupMapper.extractCruiseGroupPicturesFromAddingCruiseGroup(addCruiseGroupDto);
        this.cruiseGroupManager.addCruiseGroup(addCruiseGroupDto.getCompanyName(), addCruiseGroupDto.getName(), addCruiseGroupDto.getNumberOfSeats(),
                addCruiseGroupDto.getPrice(),cruiseAddress,cruisePicture);
    }

    @Override
    public changeCruiseGroupDto changeCruiseGroup(changeCruiseGroupDto changeCruiseGroupDto)throws BaseAppException{
        CruiseAddress start_address= new CruiseAddress(changeCruiseGroupDto.getCruiseAddress().getStreet(),changeCruiseGroupDto.getCruiseAddress().getStreetNumber(),
                changeCruiseGroupDto.getCruiseAddress().getHarborName(),changeCruiseGroupDto.getCruiseAddress().getCityName(),
                changeCruiseGroupDto.getCruiseAddress().getCountryName());
        return CruiseGroupMapper.toChangeCruiseGroupDto(cruiseGroupManager.changeCruiseGroup(changeCruiseGroupDto.getName(), changeCruiseGroupDto.getNumberOfSeats()
        ,changeCruiseGroupDto.getPrice(),start_address,changeCruiseGroupDto.getVersion()));
    }

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
