package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups.AddCruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups.CruiseGroupWithDetailsDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups.DeactivateCruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups.ChangeCruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.CruiseGroupEndpointLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.security.ETagFilterBinding;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.IntegrityUtils.checkEtagIntegrity;
import static pl.lodz.p.it.ssbd2021.ssbd03.utils.TransactionRepeater.tryAndRepeat;

@Path("/cruiseGroup")
@RequestScoped
public class CruiseGroupController {
    @Inject
    private CruiseGroupEndpointLocal cruiseGroupEndpoint;

    /**
     * Dodaje wycieczkę
     *  @param addCruiseGroupDto obiekt dto pozwalający utworzyć wycieczkę
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @POST
    @Path("/add-cuise-group")
    public void addCruiseGroup(@NotNull(message = CONSTRAINT_NOT_NULL) @Valid AddCruiseGroupDto addCruiseGroupDto) throws BaseAppException {
        tryAndRepeat(cruiseGroupEndpoint, () -> cruiseGroupEndpoint.addCruiseGroup(addCruiseGroupDto));
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
        return tryAndRepeat(cruiseGroupEndpoint, () -> cruiseGroupEndpoint.getCruiseGroupsInfo());
    }

    /**
     * Metoda pośrednio odpowiedzialna za deaktywacje grupy wycieczek
     *
     * @param deactivateCruiseGroupDto Obiekt z uuid grupy wyceczek do deaktywacji oraz wersją
     * @param etag                     Wartość etaga
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @ETagFilterBinding
    @PUT
    @Path("/deactivate-cruise-group")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deactivateCruiseGroup(@Valid DeactivateCruiseGroupDto deactivateCruiseGroupDto, @HeaderParam("If-Match") @NotNull(message = CONSTRAINT_NOT_NULL) @NotEmpty(message = CONSTRAINT_NOT_EMPTY) @Valid String etag) throws BaseAppException {
        checkEtagIntegrity(deactivateCruiseGroupDto, etag);
        tryAndRepeat(cruiseGroupEndpoint, () -> cruiseGroupEndpoint.deactivateCruiseGroup(deactivateCruiseGroupDto.getUuid(), deactivateCruiseGroupDto.getVersion()));
    }

    /**
     * Metoda pobierająca listę grup wycieczek należacych do danej firmy
     * @param companyName nazwa firmy
     * @return  lista dto grup wycieczek
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @GET
    @Path("/CruiseGroupForBusinessWorker/{companyName}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CruiseGroupWithDetailsDto> getCruiseGroupForBusinessWorker(@PathParam("companyName") @Valid String companyName) throws BaseAppException {
        return tryAndRepeat(cruiseGroupEndpoint, () -> cruiseGroupEndpoint.getCruiseGroupForBusinessWorker(companyName));
    }

    /**
     * Zmień dane wybranej grupy wycieczek
     *
     * @param dto  obiekt dto z nowymi danymi
     * @param etag Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
     */
    @PUT
    @Path("/change-cruise-group")
    @Consumes(MediaType.APPLICATION_JSON)
    @ETagFilterBinding
    public void changeCruiseGroupData(@NotNull(message = CONSTRAINT_NOT_NULL) @Valid ChangeCruiseGroupDto dto,
                                      @HeaderParam("If-Match") String etag) throws BaseAppException {
        checkEtagIntegrity(dto, etag);
        tryAndRepeat(cruiseGroupEndpoint, () -> cruiseGroupEndpoint.changeCruiseGroup(dto));
    }
}
