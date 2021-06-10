package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints.BaseEndpoint;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.DeactivateCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.EditCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.NewCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.PublishCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.CruiseMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.CruiseManagerLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Klasa który zajmuje się obsługą obiektów dto z zakresu wycieczek (rejsów)
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(TrackingInterceptor.class)
public class CruiseEndpoint extends BaseEndpoint implements CruiseEndpointLocal {

    @Inject
    private CruiseManagerLocal cruiseManagerLocal;


    @RolesAllowed("addCruise")
    @Override
    public void addCruise(NewCruiseDto newCruiseDto) throws BaseAppException {
        cruiseManagerLocal.addCruise(CruiseMapper.mapNewCruiseDtoToCruise(newCruiseDto), newCruiseDto.getCruiseName());
    }

    @RolesAllowed("deactivateCruise")
    @Override
    public void deactivateCruise(DeactivateCruiseDto deactivateCruiseDto) throws BaseAppException {
        cruiseManagerLocal.deactivateCruise(deactivateCruiseDto.getUuid(), deactivateCruiseDto.getVersion());

    }

    @PermitAll
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

    @RolesAllowed("editCruise")
    @Override
    public void editCruise(EditCruiseDto editCruiseDto) throws BaseAppException {
        cruiseManagerLocal.editCruise(editCruiseDto.getDescription(), editCruiseDto.getStartDate(), editCruiseDto.getEndDate(),
                editCruiseDto.getUuid(), editCruiseDto.getVersion());
    }

    @PermitAll
    @Override
    public List<CruiseDto> getPublishedCruises() {
        return cruiseManagerLocal.getPublishedCruises().stream().map(CruiseMapper::mapCruiseToCruiseDto).collect(Collectors.toList());
    }
}
