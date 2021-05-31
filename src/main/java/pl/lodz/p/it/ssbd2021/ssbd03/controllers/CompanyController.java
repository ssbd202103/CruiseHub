package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.CompanyEndpointLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static pl.lodz.p.it.ssbd2021.ssbd03.utils.TransactionRepeater.tryAndRepeat;

@Path("/company")
@RequestScoped
public class CompanyController {
    @Inject
    private CompanyEndpointLocal companyEndpoint;

    /**
     * Pobiera informacje o firmach
     * @return Lista firm
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @GET
    @Path("/companies-info")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CompanyLightDto> getAllCompaniesInfo() throws BaseAppException {
        return tryAndRepeat(() -> companyEndpoint.getCompaniesInfo());
    }
}
