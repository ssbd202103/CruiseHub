package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.DeactivateCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.NewCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.PublishCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.CruiseMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.CruiseManagerLocal;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import java.util.UUID;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 * Klasa który zajmuje się obsługą obiektów dto z zakresu wycieczek (rejsów)
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CruiseEndpoint implements CruiseEndpointLocal {

    @Inject
    private CruiseManagerLocal cruiseManagerLocal;

    @Override
    public void addCruise(NewCruiseDto newCruiseDto) throws BaseAppException {
        cruiseManagerLocal.addCruise(CruiseMapper.mapNewCruiseDtoToCruise(newCruiseDto), newCruiseDto.getCruiseName());
    }

    @Override
    public void deactivateCruise(DeactivateCruiseDto deactivateCruiseDto) throws BaseAppException {
        cruiseManagerLocal.deactivateCruise(deactivateCruiseDto.getUuid(), deactivateCruiseDto.getVersion());

    }

    @Override
    public CruiseDto getCruise(UUID uuid) throws BaseAppException {
        Cruise cruise = cruiseManagerLocal.getCruise(uuid);

        return CruiseMapper.mapCruiseToCruiseDto(cruise);
    }

    @RolesAllowed("publishCruise")
    @Override
    public void publishCruise(PublishCruiseDto publishCruiseDto) throws BaseAppException {
        // todo finish implementation
    }
}
