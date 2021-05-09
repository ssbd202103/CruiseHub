package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDtoForList;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.UnblockAccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.IdDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.ChangeAccessLevelStateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.GrantAccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccountDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.AccountEndpointLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.security.ETagFilterBinding;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.Response.Status.*;

import static javax.ws.rs.core.Response.Status.*;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.ETAG_IDENTITY_INTEGRITY_ERROR;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.SERIALIZATION_PARSING_ERROR;


/**
 * Klasa która udostępnia api RESTowe do wykonania operacji na kontach użytkowników, oraz zajmuje się walidacją danych.
 */
@Path("/account")
@RequestScoped
public class AccountController {
    private final ObjectMapper mapper = new ObjectMapper(); // for polymorphic Jackson serialization,
    // should not be used anymore once default JSON serializer is set to Jackson

    @EJB
    private AccountEndpointLocal accountEndpoint;

    /**
     * Pobiera użytkownika po loginie oraz tworzy ETaga na jego podstawie
     *
     * @param login użytkownika
     * @return Odpowiedź serwera z reprezentacją JSON obiektu użytkownika
     * oraz nagłówkiem ETag wygenerowanym na podstawie obiektu
     */
    @GET
    @Path("/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountByLogin(@PathParam("login") @Login String login) {
        try {
            AccountDto account = accountEndpoint.getAccountByLogin(login);
            String ETag = accountEndpoint.getETagFromSignableEntity(account);
            return Response.ok().entity(account).header("ETag", ETag).build();
        } catch (BaseAppException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Pobiera dane użytkownika wraz z szczegółami i poziomami dostępu
     *
     * @param login Login użytkownika
     * @return Odpowiedź serwera z reprezentacją JSON obiektu użytkownika
     */
    @GET
    @Path("/details-view/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountDetailsByLogin(@PathParam("login") String login) {
        try {
            AccountDetailsViewDto account = accountEndpoint.getAccountDetailsByLogin(login);
            return Response.ok().entity(mapper.writeValueAsString(account)).build();
        } catch (BaseAppException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        } catch (JsonProcessingException e) {
            return Response.status(BAD_REQUEST).entity(SERIALIZATION_PARSING_ERROR).build();
        }
    }

    /**
     * Stwórz nowe konto z poziomem dostępu Klient
     *
     * @param clientForRegistrationDto Zbiór danych niezbędnych dla stworzenia konta z poziomem dostępu Klient
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
     * @param businessWorkerForRegistrationDto Zbiór danych niezbędnych dla stworzenia konta z poziomem dostępu Pracownik firmy
     */
    @POST
    @Path("/business-worker/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createBusinessWorker(@Valid @NotNull BusinessWorkerForRegistrationDto businessWorkerForRegistrationDto) {
        accountEndpoint.createBusinessWorkerAccount(businessWorkerForRegistrationDto);
    }

    /**
     * Pobierz informacje o wszystkich kontach
     *
     * @return Lista kont
     */
    @GET
    @Path("/accounts")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AccountDtoForList> getAllAccounts() {
        return accountEndpoint.getAllAccounts();
    }


    @POST
    @Path("/block/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void blockUser(@Valid @NotNull IdDto id) {
        accountEndpoint.blockUser(id);
    }

    /**
     * Dodaj poziom dostępu do istniejącego konta
     *
     * @param grantAccessLevel Obiekt przesyłowy danych potrzebnych do nadania poziomu dostępu
     * @return Odpowiedź serwera reprezentująca obiekt AccountDto po zmianach w postaci JSON
     */
    @ETagFilterBinding
    @PUT
    @Path("/grant-access-level")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response grantAccessLevel(GrantAccessLevelDto grantAccessLevel) {
        try {
            AccountDto account = accountEndpoint.grantAccessLevel(grantAccessLevel);
            return Response.ok().entity(account).build();
        } catch (BaseAppException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Zmień stan danego poziomu dostępu (włącz/wyłącz)
     * @param changeAccessLevelStateDto Obiekt przesyłowy danych potrzebnych do zmiany stanu poziomu dostępu
     * @param etag Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
     * @return Odpowiedź serwera reprezentująca obiekt AccountDto po zmianach w postaci JSON
     */
    @ETagFilterBinding
    @PUT
    @Path("/change-access-level-state")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeAccessLevelState(ChangeAccessLevelStateDto changeAccessLevelStateDto, @HeaderParam("If-Match") String etag) {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, changeAccessLevelStateDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }
        try {
            AccountDto account = accountEndpoint.changeAccessLevelState(changeAccessLevelStateDto);
            return Response.ok().entity(account).build();
        } catch (BaseAppException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


    /**
     * Metoda odpowiedzialna za resetowanie hasła
     *
     * @param passwordResetDto obiekt dto przechowujący niezbędne dane do resetowania hasła
     * @return Odpowiedź serwera w postaci JSON
     */
    @PUT
    @Path("/reset-password")
    public Response resetPassword(@Valid PasswordResetDto passwordResetDto) {
        try {
            this.accountEndpoint.resetPassword(passwordResetDto);
        } catch (BaseAppException e) {
            Response.status(FORBIDDEN).entity(e.getMessage()).build(); // todo send key
        }
        return Response.ok().build(); // todo appropriate condition
    }

    /**
     * Metoda odpowiedzialna za zgłoszenia życzenia resetowania hasła
     *
     * @param login login użytkownika
     * @return Odpowiedź serwera w postaci JSON
     */
    @POST
    @Path("/request-password-reset/{login}")
    public Response requestPasswordReset(@PathParam("login") @Login String login) {
        try {
            this.accountEndpoint.requestPasswordReset(login);
        } catch (BaseAppException e) {
            Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    /**
     * @param unblockAccountDto  Obiekt posiadający login użytkownika którego mamy odblokować
     * @param tagValue Wartość etaga
     * @return Zwraca kod potwierdzający poprawne bądź niepoprawne wykoannie
     * @throws BaseAppException Wyjątek rzucany w momencie kiedy nie znajdzie takich użytkowników
     */
    @ETagFilterBinding
    @PUT
    @Path("/unblock")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response unblockUser(UnblockAccountDto unblockAccountDto, @HeaderParam("If-Match") @NotNull @NotEmpty String tagValue) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(tagValue, unblockAccountDto)) {
            return Response.status(406).build();
        }
        accountEndpoint.unblockUser(unblockAccountDto.getLogin(), unblockAccountDto.getVersion());
        return Response.status(200).build();
    }


    /**
     * Metoda odpowiedzialna za zgłoszenia życzenia resetowania hasła dla danego użytkownika
     *
     * @param login login użytkownika
     * @param email e-mail użytkownika
     * @return Odpowiedź serwera w postaci JSON
     */
    @POST
    @Path("/request-someones-password-reset/{login}/{email}")
    public Response requestSomeonesPasswordReset(@PathParam("login") @Login String login, @PathParam("email") @Email String email) {
        try {
            this.accountEndpoint.requestSomeonesPasswordReset(login, email);
        } catch (BaseAppException e) {
            Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

}