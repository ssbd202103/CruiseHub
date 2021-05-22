package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.DeactivateCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.NewCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.CruiseMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.CruiseManagerLocal;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.UUID;
import javax.inject.Inject;

/**
 * Klasa który zajmuje się obsługą obiektów dto z zakresu wycieczek (rejsów)
 */
@Stateful
public class CruiseEndpoint implements CruiseEndpointLocal {

    @Inject
    private CruiseManagerLocal cruiseManagerLocal;


    @Override
    public void addCruise(NewCruiseDto newCruiseDto) throws BaseAppException {
        cruiseManagerLocal.addCruise(CruiseMapper.mapNewCruiseDtoToCruise(newCruiseDto));
    }

    @Override
    public void deactivateCruise(DeactivateCruiseDto deactivateCruiseDto) throws BaseAppException {
        cruiseManagerLocal.deactivateCruise(deactivateCruiseDto.getUuid(), deactivateCruiseDto.getVersion());

    }



}
