package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;


import pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints.BaseEndpoint;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.*;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups.CruiseGroupWithDetailsDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups.AddCruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups.changeCruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.CruiseGroupMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.CruiseGroupManagerLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * Klasa która zajmuje się gromadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z grupami wycieczek oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Stateful
@Interceptors(TrackingInterceptor.class)
public class CruiseGroupEndpoint extends BaseEndpoint implements CruiseGroupEndpointLocal {

    @Inject
    private CruiseGroupManagerLocal cruiseGroupManager;

    @Override
    @RolesAllowed("addCruiseGroup")
    public void addCruiseGroup(AddCruiseGroupDto addCruiseGroupDto) throws BaseAppException {
        CruiseAddress cruiseAddress = CruiseGroupMapper.extractAddressForAddingCruiseGroup(addCruiseGroupDto);
        List<CruisePicture> cruisePicture = CruiseGroupMapper.extractCruiseGroupPicturesFromAddingCruiseGroup(addCruiseGroupDto);
        this.cruiseGroupManager.addCruiseGroup(addCruiseGroupDto.getCompanyName(), addCruiseGroupDto.getName(), addCruiseGroupDto.getNumberOfSeats(),
                addCruiseGroupDto.getPrice(), cruiseAddress, cruisePicture,addCruiseGroupDto.getDescription());
    }

    @Override
    @RolesAllowed("changeCruiseGroup")
    public void changeCruiseGroup(changeCruiseGroupDto changeCruiseGroupDto) throws BaseAppException {
        CruiseAddress start_address = new CruiseAddress(changeCruiseGroupDto.getCruiseAddress().getStreet(), changeCruiseGroupDto.getCruiseAddress().getStreetNumber(),
                changeCruiseGroupDto.getCruiseAddress().getHarborName(), changeCruiseGroupDto.getCruiseAddress().getCityName(),
                changeCruiseGroupDto.getCruiseAddress().getCountryName());
                if(changeCruiseGroupDto.getPicture() != null){
                    String encodedImg = changeCruiseGroupDto.getPicture().getDataURL().split(",")[1];
                    byte[] decoded = Base64.getDecoder().decode(encodedImg);
                    CruisePicture picture = new CruisePicture(decoded,"test");
                    cruiseGroupManager.changeCruiseGroup(changeCruiseGroupDto.getName(), changeCruiseGroupDto.getNumberOfSeats()
                            , changeCruiseGroupDto.getPrice(), start_address, changeCruiseGroupDto.getVersion(),changeCruiseGroupDto.getDescription(), picture,changeCruiseGroupDto.getUuid());
                }else
                {
                    CruisePicture picture = new CruisePicture();
                    byte[] array = new byte[0];
                    picture.setImg(array);
                    cruiseGroupManager.changeCruiseGroup(changeCruiseGroupDto.getName(), changeCruiseGroupDto.getNumberOfSeats()
                            , changeCruiseGroupDto.getPrice(), start_address, changeCruiseGroupDto.getVersion(),changeCruiseGroupDto.getDescription(), picture,changeCruiseGroupDto.getUuid());
                }


    }


    @Override
    @RolesAllowed("getAllCruiseGroupList")
    public List<CruiseGroupWithDetailsDto> getCruiseGroupsInfo() throws FacadeException, BaseAppException {
        List<CruiseGroupWithDetailsDto> res = new ArrayList<>();
        for (CruiseGroup cruiseGroup : cruiseGroupManager.getAllCruiseGroups()) {
            List<Cruise> cruise = cruiseGroupManager.getCruiseBelongsToCruiseGroup(cruiseGroup);
            res.add(CruiseGroupMapper.toCruiseGroupWithDetailsDto(cruiseGroup,cruise));
        }
        return res;
    }

    @RolesAllowed("deactivateCruiseGroup")
    @Override
    public void deactivateCruiseGroup(UUID uuid, Long version) throws BaseAppException {
        this.cruiseGroupManager.deactivateCruiseGroup(uuid, version);
    }
    @RolesAllowed("getCruiseGroupForBusinessWorker")
    @Override
    public List<CruiseGroupWithDetailsDto> getCruiseGroupForBusinessWorker(String companyName) throws BaseAppException {
        List<CruiseGroupWithDetailsDto> res = new ArrayList<>();
        for (CruiseGroup cruiseGroup : cruiseGroupManager.getCruiseGroupForBusinessWorker(companyName)) {
            List<Cruise> cruise = cruiseGroupManager.getCruiseBelongsToCruiseGroup(cruiseGroup);
            res.add(CruiseGroupMapper.toCruiseGroupWithDetailsDto(cruiseGroup,cruise));
        }
      return res;
    }
}
