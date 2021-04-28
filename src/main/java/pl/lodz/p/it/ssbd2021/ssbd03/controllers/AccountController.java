package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.AccountManagerException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.AccountEndpointLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.security.ETagFilterBinding;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.ACCESS_LEVEL_DOES_NOT_EXIST_ERROR;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.ACCESS_LEVEL_NOT_ASSIGNABLE_ERROR;


/**
 * Klasa która udostępnia api RESTowe do wykonania operacji na kontach użytkowników, oraz zajmuje się walidacją danych.
 */
@Path("/account")
@RequestScoped
public class AccountController {

    @EJB
    private AccountEndpointLocal accountEndpoint;


    /**
     * Stwórz nowe konto z poziomem dostępu Klient
     *
     * @param clientForRegistrationDto zbiór danych niezbędnych dla stworzenia konta z poziomem dostępu Klient
     */
    @POST
    @Path("/client/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createClient(@Valid @NotNull ClientForRegistrationDto clientForRegistrationDto) {
        accountEndpoint.createClientAccount(clientForRegistrationDto);
    }

    /**
     * Stwórz nowe konto z poziomem dostępu Pracownik firmy
     *
     * @param businessWorkerForRegistrationDto zbiór danych niezbędnych dla stworzenia konta z poziomem dostępu Pracownik firmy
     */
    @POST
    @Path("/businessworker/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createBusinessWorker(@Valid @NotNull BusinessWorkerForRegistrationDto businessWorkerForRegistrationDto) {
        accountEndpoint.createBusinessWorkerAccount(businessWorkerForRegistrationDto);
    }

    //    @ETagFilterBinding
    @PUT
    @Path("/{login}/grantAccessLevel/{accessLevel}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response grantAccessLevel(@PathParam("login") @Login String accountLogin, @PathParam("accessLevel") String accessLevel) throws BaseAppException {
        if (Arrays.stream(AccessLevelType.values()).noneMatch(accessLevelType -> accessLevelType.name().equalsIgnoreCase(accessLevel))) {
            return Response.status(400).entity(ACCESS_LEVEL_DOES_NOT_EXIST_ERROR).build();
        }

        try {
            if (accessLevel.toLowerCase().equalsIgnoreCase(AccessLevelType.ADMINISTRATOR.name())) {
                AccountDto account = accountEndpoint.grantAdministratorAccessLevel(accountLogin);
                return Response.ok().entity(account).build();
            }

            if (accessLevel.toLowerCase().equalsIgnoreCase(AccessLevelType.MODERATOR.name())) {
                AccountDto account = accountEndpoint.grantModeratorAccessLevel(accountLogin);
                return Response.ok().entity(account).build();
            }
        } catch (AccountManagerException e) {
            return Response.status(400).entity(e.getMessage()).build();
        }

        return Response.status(400).entity(ACCESS_LEVEL_NOT_ASSIGNABLE_ERROR).build();
    }

    @GET
    @Path("/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountDto getAccountByLogin(@PathParam("login") @Login String login) throws BaseAppException {
        return accountEndpoint.getAccountByLogin(login);
    }
}