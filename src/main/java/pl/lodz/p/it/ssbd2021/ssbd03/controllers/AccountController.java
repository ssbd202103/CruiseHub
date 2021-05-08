package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.AccountChangeEmailDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.AdministratorChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.BusinessWorkerChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.ClientChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.ModeratorChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.AdministratorForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ModeratorForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.AccountEndpointLocal;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.persistence.OptimisticLockException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.*;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.security.ETagFilterBinding;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.ETAG_IDENTITY_INTEGRITY_ERROR;

/**
 * Klasa która udostępnia api RESTowe do wykonania operacji na kontach użytkowników, oraz zajmuje się walidacją danych.
 */
@Path("/account")
@RequestScoped
public class AccountController {

    @EJB
    private AccountEndpointLocal accountEndpoint;

    @GET
    @Path("/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountByLogin(@PathParam("login") @Login String login) throws BaseAppException {
        try {
            AccountDto account = accountEndpoint.getAccountByLogin(login);
            String ETag = accountEndpoint.getETagFromSignableEntity(account);
            return Response.ok().entity(account).header("ETag", ETag).build();
        } catch (BaseAppException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/client/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientByLogin(@PathParam("login") @Login String login) throws BaseAppException {
        try {
            ClientDto client = accountEndpoint.getClientByLogin(login);
            String Etag = accountEndpoint.getETagFromSignableEntity(client);
            return Response.ok().entity(client).header("ETag", Etag).build();
        } catch (BaseAppException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/businessworker/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBusinessWorkerByLogin(@PathParam("login") @Login String login) throws BaseAppException {
        try {
            BusinessWorkerDto businessWorker = accountEndpoint.getBusinessWorkerByLogin(login);
            String ETag = accountEndpoint.getETagFromSignableEntity(businessWorker);
            return Response.ok().entity(businessWorker).header("ETag", ETag).build();
        } catch (BaseAppException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/moderator/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModeratorByLogin(@PathParam("login") @Login String login) throws BaseAppException {
        try {
            ModeratorDto moderator = accountEndpoint.getModeratorByLogin(login);
            String ETag = accountEndpoint.getETagFromSignableEntity(moderator);
            return Response.ok().entity(moderator).header("ETag", ETag).build();
        } catch (BaseAppException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/administrator/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdministratorByLogin(@PathParam("login") @Login String login) throws BaseAppException {
        try {
            AdministratorDto administrator = accountEndpoint.getAdministratorByLogin(login);
            String ETag = accountEndpoint.getETagFromSignableEntity(administrator);
            return Response.ok().entity(administrator).header("ETag", ETag).build();
        } catch (BaseAppException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }

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
     * Zmień mail według podanych w dto danych
     *
     * @param accountChangeEmailDto obiekt dto z loginem, nowym mailem oraz wersją
     */
    @PUT
    @Path("/change_email")
    @Consumes(MediaType.APPLICATION_JSON)
    public void changeEmail(AccountChangeEmailDto accountChangeEmailDto) {
        accountEndpoint.changeEmail(accountChangeEmailDto);
    }

    /**
     * Zmień dane konta klienta
     *
     * @param clientChangeDataDto obiekt dto z nowymi danymi
     */
    @PUT
    @Path("/client/changedata")
    @Consumes(MediaType.APPLICATION_JSON)
    @ETagFilterBinding
    public Response changeClientData(ClientChangeDataDto clientChangeDataDto, @HeaderParam("If-Match") String etag) {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, clientChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }

        try {
            accountEndpoint.changeClientData(clientChangeDataDto);
        } catch (EJBException | OptimisticLockException e) {
            return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (BaseAppException e) {
            return  Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }

        return Response.noContent().build();
    }

    /**
     * Zmień dane konta pracownika firmy
     *
     * @param businessWorkerChangeDataDto obiekt dto z nowymi danymi
     */
    @PUT
    @Path("/businessworker/changedata")
    @Consumes(MediaType.APPLICATION_JSON)
    @ETagFilterBinding
    public Response changeBusinessWorkerData(BusinessWorkerChangeDataDto businessWorkerChangeDataDto, @HeaderParam("If-Match") String etag) {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, businessWorkerChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }

        try {
            accountEndpoint.changeBusinessWorkerData(businessWorkerChangeDataDto);
        } catch (EJBException | OptimisticLockException e) {
            return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (BaseAppException e) {
            return  Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }

        return Response.noContent().build();
    }

    /**
     * Zmień dane konta moderatora
     *
     * @param moderatorChangeDataDto obiekt dto z nowymi danymi
     */
    @PUT
    @Path("/moderator/changedata")
    @Consumes(MediaType.APPLICATION_JSON)
    @ETagFilterBinding
    public Response changeModeratorData(ModeratorChangeDataDto moderatorChangeDataDto,  @HeaderParam("If-Match") String etag) {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, moderatorChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }

        try {
            accountEndpoint.changeModeratorData(moderatorChangeDataDto);
        } catch (EJBException | OptimisticLockException e) {
            return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (BaseAppException e) {
            return  Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }

        return Response.noContent().build();
    }

    /**
     * Zmień dane konta administratora
     *
     * @param administratorChangeDataDto obiekt dto z nowymi danymi
     */
    @PUT
    @Path("/administrator/changedata")
    @Consumes(MediaType.APPLICATION_JSON)
    @ETagFilterBinding
    public Response changeAdministratorData(AdministratorChangeDataDto administratorChangeDataDto, @HeaderParam("If-Match") String etag) {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, administratorChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }

        try {
            accountEndpoint.changeAdministratorData(administratorChangeDataDto);
        } catch (EJBException | OptimisticLockException e) {
            return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (BaseAppException e) {
            return  Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }

        return Response.noContent().build();
    }

    @GET
    @Path("/greeting")
    public String greeting() {
        return "Hello SSBD";
    }
}