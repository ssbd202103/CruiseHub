package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.BusinessWorkerDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.changeCruiseGroup.CompanyDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.CompanyEndpointLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
     *
     * @return Lista firm
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @GET
    @Path("/companies-info")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CompanyLightDto> getAllCompaniesInfo() throws BaseAppException {
        return tryAndRepeat(() -> companyEndpoint.getCompaniesInfo());
    }

    /**
     * Pobiera informacje o firmach
     *
     * @return Lista firm
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @GET
    @Path("/companies")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CompanyDto> getAllCompanies() throws BaseAppException {
        return tryAndRepeat(() -> companyEndpoint.getAllCompanies());
    }

    /**
     * Pobiera informacje o pracownikach danej firmy
     *
     * @param companyName Nazwa firmy
     * @return Lista pracowników firmy w postaci reprezentacji DTO
     * @throws BaseAppException Bazowy wyjątek aplikacyjny występujący przy nieznalezieniu firmy lub naruszeniu zasad biznesowych
     */
    @GET
    @Path("/{companyName}/business-workers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BusinessWorkerDto> getBusinessWorkersForCompany(@PathParam("companyName") String companyName) throws BaseAppException {
        return tryAndRepeat(() -> companyEndpoint.getBusinessWorkersForCompany(companyName));
    }


}
