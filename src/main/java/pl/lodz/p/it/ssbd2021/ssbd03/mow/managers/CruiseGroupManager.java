package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseAddress;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruisePicture;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.changeCruiseGroup.changeCruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseGroupFacadeMow;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.Set;

@Stateful
public class CruiseGroupManager implements  CruiseGroupManagerLocal{
    @EJB
    private CruiseGroupFacadeMow CruiseGroup;

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
}
