package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountChangeEmailDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.AdministratorForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ModeratorForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.AccountEndpointLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


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

    /**
     * Stwórz nowe konto z poziomem dostępu Administrator.
     *
     * @param administratorForRegistrationDto zbiór danych niezbędnych dla stworzenia konta z poziomem dostępu Administrator
     */
    @POST
    @Path("/administrator/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createAdministrator(@Valid @NotNull AdministratorForRegistrationDto administratorForRegistrationDto) {
        accountEndpoint.createAdministratorAccount(administratorForRegistrationDto);
    }

    /**
     * Stwórz nowe konto z poziomem dostępu Moderator.
     *
     * @param moderatorForRegistrationDto zbiór danych niezbędnych dla stworzenia konta z poziomem dostępu Moderator
     */
    @POST
    @Path("/moderator/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createModerator(@Valid @NotNull ModeratorForRegistrationDto moderatorForRegistrationDto) {
        accountEndpoint.createModeratorAccount(moderatorForRegistrationDto);
    }

    /**
     * Zmień hasło webług podanych w dto danych
     *
     * @param accountChangeEmailDto obiekt dto z loginem, nowym mailem oraz wersją
     */
    @PUT
    @Path("/change_email")
    @Consumes(MediaType.APPLICATION_JSON)
    public void changeEmail(AccountChangeEmailDto accountChangeEmailDto) {
        accountEndpoint.changeEmail(accountChangeEmailDto);
    }

    @GET
    @Path("/greeting")
    public String greeting() {
        return "Hello SSBD";
    }
}