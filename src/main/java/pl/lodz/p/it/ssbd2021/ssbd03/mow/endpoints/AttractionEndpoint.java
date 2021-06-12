package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints.BaseEndpoint;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.AddAttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.AttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.EditAttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.AttractionMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.AttractionManagerLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Klasa która zajmuje się gromadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z atrakcjami oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */

@Stateful
@Interceptors(TrackingInterceptor.class)
public class AttractionEndpoint extends BaseEndpoint implements AttractionEndpointLocal {

    @Inject
    private AttractionManagerLocal attractionManager;


    @RolesAllowed("deleteAttraction")
    @Override
    public void deleteAttraction(String name) throws BaseAppException {
        this.attractionManager.deleteAttraction(name);
    }


    @RolesAllowed("addAttraction")
    @Override
    public void addAttraction(AddAttractionDto addAttractionDto) throws BaseAppException {
        throw new UnsupportedOperationException();
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