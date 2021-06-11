package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups.AddCruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups.CruiseGroupWithDetailsDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.CruiseGroupEndpointLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.utils.TransactionRepeater.tryAndRepeat;

@Path("/cruiseGroup")
@RequestScoped
public class CruiseGroupController {
    @Inject
    private CruiseGroupEndpointLocal cruiseGroupEndpoint;

    /**
     *Dodaje wycieczkę
     *
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @POST
    @Path("/add-cuise-group")
    public void addCruiseGroup(@NotNull(message = CONSTRAINT_NOT_NULL) @Valid AddCruiseGroupDto addCruiseGroupDto) throws BaseAppException {
         tryAndRepeat(() -> cruiseGroupEndpoint.addCruiseGroup(addCruiseGroupDto));
    }

    /**
     * Pobierz informacje o wszystkich grupach wycieczek
     *
     * @return Lista kont
     */
    @GET
    @Path("/cruise-groups")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CruiseGroupWithDetailsDto> getAllCruiseGroups() throws BaseAppException {
        return tryAndRepeat(() -> cruiseGroupEndpoint.getCruiseGroupsInfo());
    }
}
