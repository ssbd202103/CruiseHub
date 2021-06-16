package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;


import pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints.BaseEndpoint;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.MapperException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.AddAttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.AttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.EditAttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.AttractionMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.AttractionManagerLocal;
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

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CRUISE_MAPPER_UUID_PARSE;

/**
 * Klasa która zajmuje się gromadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z atrakcjami oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(TrackingInterceptor.class)
public class AttractionEndpoint extends BaseEndpoint implements AttractionEndpointLocal {

    @Inject
    private AttractionManagerLocal attractionManager;

    @Inject
    private CruiseManagerLocal cruiseManager;

    @RolesAllowed("deleteAttraction")
    @Override
    public void deleteAttraction(String name) throws BaseAppException {
        this.attractionManager.deleteAttraction(name);
    }


    @RolesAllowed("addAttraction")
    @Override
    public void addAttraction(AddAttractionDto addAttractionDto) throws BaseAppException {
        try {
            Cruise cruise = cruiseManager.getCruise(UUID.fromString(addAttractionDto.getCruiseUUID()));
            attractionManager.addAttraction(AttractionMapper.toAttraction(addAttractionDto, cruise));
        } catch (IllegalArgumentException e) {
            throw new MapperException(CRUISE_MAPPER_UUID_PARSE);
        }
    }

    @RolesAllowed("editAttraction")
    @Override
    public void editAttraction(EditAttractionDto editAttractionDto) throws BaseAppException {
        throw new UnsupportedOperationException();
    }

    @PermitAll
    @Override
    public List<AttractionDto> getAttractionsByCruiseUUID(UUID uuid) throws BaseAppException {
        return attractionManager.findByCruiseUUID(uuid).stream().map(AttractionMapper::toAttractionDto).collect(Collectors.toList());
    }
}