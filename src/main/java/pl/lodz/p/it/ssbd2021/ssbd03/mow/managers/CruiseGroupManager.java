package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseAddress;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruisePicture;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.changeCruiseGroup.changeCruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseGroupFacadeMow;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Klasa która zarządza logiką biznesową grupy wycieczek
 */
@Stateful
public class CruiseGroupManager implements CruiseGroupManagerLocal {

    @EJB
    private CruiseGroupFacadeMow cruiseGroupFacadeMow;

    @Context
    private SecurityContext context;

    @Inject
    I18n i18n;

    @Override
    public void addCruiseGroup(String companyName, String name, long number_of_seats, Double price, CruiseAddress start_address, List<CruisePicture> pictures){
            //todo
    }
    @Override
    public CruiseGroup changeCruiseGroup(String name, long number_of_seats, Double price, CruiseAddress start_address, long version){
        return null; //todo
    }

    //TODO mozliwe wypisanie wszytchi grup wycieczek.
/*    @Override
    public List<CruiseGroup> getAllCruiseGroups() {
        return cruiseGroupFacadeMow.findAll().stream().map(o -> (CruiseGroup) o).collect(Collectors.toList());
    }*/

    @Override
    public CruiseGroup deactivateCruiseGroup(String name, Long version) throws BaseAppException {
        CruiseGroup cruiseGroup = this.cruiseGroupFacadeMow.findByName(name);
        if (!cruiseGroup.getVersion().equals(version)) {
            throw FacadeException.optimisticLock();
        }
        cruiseGroup.setActive(false);
        //TODO SetAlerted by and alertyed type or something

        /*        setAlterTypeAndAlterAccount(cruiseGroup, cruiseGroupFacadeMow.getAlterTypeWrapperByAlterType(AlterType.UPDATE),
                // this is for now, will be changed in the upcoming feature
                cruiseGroup);
        cruiseGroupFacadeMow.edit(cruiseGroup);
        return cruiseGroup;*/
        return cruiseGroup;
    }
    }

