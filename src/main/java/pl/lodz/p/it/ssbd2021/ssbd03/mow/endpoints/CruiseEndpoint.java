package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints.BaseEndpoint;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.CruiseGroupMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.MapperException;
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

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CRUISE_MAPPER_DATE_PARSE;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CRUISE_MAPPER_UUID_PARSE;

/**
 * Klasa który zajmuje się obsługą obiektów dto z zakresu wycieczek (rejsów)
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(TrackingInterceptor.class)
public class CruiseEndpoint extends BaseEndpoint implements CruiseEndpointLocal {

    @Inject
    private CruiseManagerLocal cruiseManager;


    @RolesAllowed("addCruise")
    @Override
    public void addCruise(NewCruiseDto newCruiseDto) throws BaseAppException {
        try {
            cruiseManager.addCruise(CruiseMapper.mapNewCruiseDtoToCruise(newCruiseDto), UUID.fromString(newCruiseDto.getCruiseGroupUUID()));
        } catch (IllegalArgumentException e) {
            throw new MapperException(CRUISE_MAPPER_UUID_PARSE);
        }

    }

    @RolesAllowed("deactivateCruise")
    @Override
    public void deactivateCruise(DeactivateCruiseDto deactivateCruiseDto) throws BaseAppException {
        try {
        cruiseManager.deactivateCruise(UUID.fromString(deactivateCruiseDto.getUuid()), deactivateCruiseDto.getVersion());
        } catch (IllegalArgumentException e) {
            throw new MapperException(CRUISE_MAPPER_UUID_PARSE);
        }

    }

    @PermitAll
    @Override
    public CruiseDto getCruise(UUID uuid) throws BaseAppException {
        Cruise cruise = cruiseManager.getCruise(uuid);

        return CruiseMapper.mapCruiseToCruiseDto(cruise);
    }

    @PermitAll
    @Override
    public List<RelatedCruiseDto> getCruisesByCruiseGroup(UUID uuid) throws BaseAppException {
        List<Cruise> cruises = cruiseManager.getCruisesByCruiseGroup(uuid);

        return cruises.stream().filter(Cruise::isActive).map(CruiseMapper::toRelatedCruiseDto).collect(Collectors.toList());
    }

    @RolesAllowed("publishCruise")
    @Override
    public void publishCruise(PublishCruiseDto publishCruiseDto) throws BaseAppException {
        // todo finish implementation
    }

    @RolesAllowed("editCruise")
    @Override
    public void editCruise(EditCruiseDto editCruiseDto) throws BaseAppException {
        cruiseManager.editCruise(editCruiseDto.getDescription(), editCruiseDto.getStartDate(), editCruiseDto.getEndDate(),
                editCruiseDto.getUuid(), editCruiseDto.getVersion());
    }

    @PermitAll
    @Override
    public List<CruiseDto> getPublishedCruises() {
        return cruiseManager.getPublishedCruises().stream()
                .map(CruiseMapper::mapCruiseToCruiseDto).collect(Collectors.toList());
    }

    @PermitAll
    @Override
    public List<CruiseForCruiseGroupDto> getCruisesForCruiseGroup(String cruiseGroupName) throws BaseAppException {
        return CruiseGroupMapper.toCruiseForCruiseGroupDtos(cruiseManager.getCruisesForCruiseGroup(cruiseGroupName));
    }

}
