package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccountDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.AccountEndpointLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/self")
@RequestScoped
public class AccountSelfController {

    @EJB
    private AccountEndpointLocal accountEndpoint;

    /**
     * Pobiera szczegółowe informacje o koncie uwierzytelnionego użytkownika
     *
     * @return Reprezentacja szczegółów konta w postaci AcccountDetailsViewDto serializowanego na JSON
     */
    @GET
    @Path("/account-details")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSelfDetails() {
        try {
            String selfLogin = accountEndpoint.getCurrentUserLogin();
            AccountDetailsViewDto account = accountEndpoint.getAccountDetailsByLogin(selfLogin);
            return Response.ok().entity(account).build();
        } catch (BaseAppException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
