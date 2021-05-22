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

import static pl.lodz.p.it.ssbd2021.ssbd03.controllers.TransactionRepeater.tryAndRepeat;

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
    public AccountDetailsViewDto getSelfDetails() throws BaseAppException {
        String selfLogin = tryAndRepeat(() -> accountEndpoint.getCurrentUserLogin());
        return tryAndRepeat(() -> accountEndpoint.getAccountDetailsByLogin(selfLogin));
    }
}
