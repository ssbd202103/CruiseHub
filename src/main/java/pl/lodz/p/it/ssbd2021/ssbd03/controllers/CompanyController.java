package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.common.dto.MetadataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.MapperException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.BusinessWorkerDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.AddCompanyDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.CompanyDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.CompanyEndpointLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;
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
        return tryAndRepeat(companyEndpoint, () -> companyEndpoint.getCompaniesInfo());
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
        return tryAndRepeat(companyEndpoint, () -> companyEndpoint.getAllCompanies());
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
        return tryAndRepeat(companyEndpoint, () -> companyEndpoint.getBusinessWorkersForCompany(companyName));
    }

    /**
     * Metoda odpowiedzialna za dodanie firmy przez moderatora
     *
     * @param addCompanyDto obiekt dto przechowujący informacje podane przez moderatora
     * @throws BaseAppException Bazowy wyjątek aplikacji rzucany w przypadku naruszenia zasad biznesowych
     */
    @POST
    @Path("/add-company")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addCompany(@NotNull(message = CONSTRAINT_NOT_NULL) @Valid AddCompanyDto addCompanyDto) throws BaseAppException {
        tryAndRepeat(companyEndpoint, () -> companyEndpoint.addCompany(addCompanyDto));
    }

    /**
     * Pobiera metadane firmy
     *
     * @param nip nip firmy wybranej do metadanych
     * @return Reprezentacja DTO metadanych
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @GET
    @Path("/metadata/{nip}")
    @Produces(MediaType.APPLICATION_JSON)
    public MetadataDto getCompanyMetadata(@PathParam("nip") String nip) throws BaseAppException {
        try {
            long companyNIP = Long.parseLong(nip);
            return tryAndRepeat(companyEndpoint, () -> companyEndpoint.getCompanyMetadata(companyNIP));
        } catch (NumberFormatException e) {
            throw new MapperException(MAPPER_LONG_PARSE);
        }
    }
}
