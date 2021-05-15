package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.NewCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.CruiseMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.CruiseManagerLocal;

import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class CruiseEndpoint implements CruiseEndpointLocal {
//    @EJB
//    private CruiseManagerLocal cruiseManagerLocal;


    @Override
    public void addCruise(NewCruiseDto newCruiseDto, Account account) throws BaseAppException {
       // cruiseManagerLocal.addCruise(CruiseMapper.mapNewCruiseDtoToCruise(newCruiseDto));
    }

}
