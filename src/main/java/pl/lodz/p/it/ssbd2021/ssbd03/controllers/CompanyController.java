package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.CompanyEndpointLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static pl.lodz.p.it.ssbd2021.ssbd03.controllers.TransactionRepeater.tryAndRepeat;

@Path("/company")
@RequestScoped
public class CompanyController {
    @Inject
    private CompanyEndpointLocal companyEndpoint;

    @GET
    @Path("/companiesinfo")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CompanyLightDto> getAllCompaniesInfo() throws BaseAppException {
        return tryAndRepeat(() -> companyEndpoint.getCompaniesInfo());
    }
}
