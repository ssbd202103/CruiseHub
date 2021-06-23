package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;


import pl.lodz.p.it.ssbd2021.ssbd03.common.dto.MetadataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints.BaseEndpoint;
import pl.lodz.p.it.ssbd2021.ssbd03.common.mappers.MetadataMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Attraction;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.MapperException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.AddAttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.AttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.EditAttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.AttractionMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.AttractionManagerLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.MAPPER_UUID_PARSE;

/**
 * Klasa która zajmuje się gromadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z atrakcjami oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(TrackingInterceptor.class)
public class AttractionEndpoint extends BaseEndpoint implements AttractionEndpointLocal {

    @Inject
    private AttractionManagerLocal attractionManager;

    @RolesAllowed("deleteAttraction")
    @Override
    public void deleteAttraction(UUID uuid) throws BaseAppException {
        this.attractionManager.deleteAttraction(uuid);
    }


    @RolesAllowed("addAttraction")
    @Override
    public UUID addAttraction(AddAttractionDto addAttractionDto) throws BaseAppException {
        try {
            UUID cruiseUUID = UUID.fromString(addAttractionDto.getCruiseUUID());
            return attractionManager.addAttraction(AttractionMapper.toAttraction(addAttractionDto), cruiseUUID);
        } catch (IllegalArgumentException e) {
            throw new MapperException(MAPPER_UUID_PARSE);
        }
    }

    @RolesAllowed("editAttraction")
    @Override
    public void editAttraction(EditAttractionDto editAttractionDto) throws BaseAppException {
        try {
            UUID cruiseUUID = UUID.fromString(editAttractionDto.getUuid());
            attractionManager.editAttraction(cruiseUUID, editAttractionDto.getNewName(), editAttractionDto.getNewDescription(),
                    editAttractionDto.getNewPrice(), editAttractionDto.getNewNumberOfSeats(), editAttractionDto.getVersion());
        } catch (IllegalArgumentException e) {
            throw new MapperException(MAPPER_UUID_PARSE);
        }
    }

    @PermitAll
    @Override
    public List<AttractionDto> getAttractionsByCruiseUUID(UUID uuid) throws BaseAppException {
        List<AttractionDto> attractions = new ArrayList<>();
        for (Attraction attraction : attractionManager.findByCruiseUUID(uuid)) {
            attractions.add(AttractionMapper.toAttractionDto(attraction));
        }
        return attractions;
    }

    @RolesAllowed("authenticatedUser")
    @Override
    public MetadataDto getAttractionMetadata(UUID uuid) throws BaseAppException {
        return MetadataMapper.toMetadataDto(attractionManager.findByUUID(uuid));
    }


}