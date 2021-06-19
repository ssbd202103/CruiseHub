package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints.BaseEndpoint;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.MapperException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.CruiseGroupMapper;
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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.MAPPER_UUID_PARSE;

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
            throw new MapperException(MAPPER_UUID_PARSE);
        }

    }

    @RolesAllowed("deactivateCruise")
    @Override
    public void deactivateCruise(DeactivateCruiseDto deactivateCruiseDto) throws BaseAppException {
        try {
            cruiseManager.deactivateCruise(UUID.fromString(deactivateCruiseDto.getUuid()), deactivateCruiseDto.getVersion());
        } catch (IllegalArgumentException e) {
            throw new MapperException(MAPPER_UUID_PARSE);
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
        try {
            cruiseManager.publishCruise(publishCruiseDto.getCruiseVersion(), UUID.fromString(publishCruiseDto.getCruiseUuid()));
        } catch (IllegalArgumentException e) {
            throw new MapperException(MAPPER_UUID_PARSE);
        }
    }

    @RolesAllowed("editCruise")
    @Override
    public void editCruise(EditCruiseDto editCruiseDto) throws BaseAppException {
        try {
            cruiseManager.editCruise(LocalDateTime.ofInstant(Instant.parse(editCruiseDto.getStartDate()), ZoneId.systemDefault()),
                    LocalDateTime.ofInstant(Instant.parse(editCruiseDto.getEndDate()), ZoneId.systemDefault()),
                    editCruiseDto.getUuid(), editCruiseDto.getVersion());
        } catch (DateTimeParseException e) {
            throw new MapperException(MAPPER_UUID_PARSE);
        }
    }

    @PermitAll
    @Override
    public List<CruiseGroupWithCruisesDto> getPublishedCruises() throws BaseAppException {
        List<Cruise> cruises = cruiseManager.getPublishedCruises();
        return CruiseMapper.toListOfCruiseGroupsWithCruisesDto(cruises);
    }

    @PermitAll
    @Override
    public List<CruiseForCruiseGroupDto> getCruisesForCruiseGroup(UUID cruiseGroupUUID) throws BaseAppException {
        return CruiseGroupMapper.toCruiseForCruiseGroupDtos(cruiseManager.getCruisesByCruiseGroup(cruiseGroupUUID));
    }

}
