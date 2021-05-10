package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDtoForList;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.UnblockAccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.ChangeAccessLevelStateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDtoForList;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.BlockAccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.OtherAccountChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.OtherBusinessWorkerChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.OtherClientChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.GrantAccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccountDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.AccountChangeEmailDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.AdministratorForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.AccountEndpointLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.security.ETagFilterBinding;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.print.attribute.standard.Media;
import javax.persistence.OptimisticLockException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.*;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.ETAG_IDENTITY_INTEGRITY_ERROR;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import static javax.ws.rs.core.Response.Status.*;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.ETAG_IDENTITY_INTEGRITY_ERROR;

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
    public void createClient(@Valid @NotNull ClientForRegistrationDto clientForRegistrationDto) throws BaseAppException {
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
    public void createBusinessWorker(@Valid @NotNull BusinessWorkerForRegistrationDto businessWorkerForRegistrationDto) throws BaseAppException {
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


    /**
     * Metoda pośrednio odpowiedzialna za blokowanie użytkownika
     * @param blockAccountDto Obiekt z loginem użytkownika do zablokowania oraz wersją
     * @param etagValue Wartość etaga
     * @return Respons wraz z kodem
     * @throws BaseAppException Wyjątek aplikacji rzucany w przypadku błędu pobrania danych użytkownika
     */
    @ETagFilterBinding
    @PUT
    @Path("/block")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response blockUser(BlockAccountDto blockAccountDto, @HeaderParam("If-Match") @NotNull @NotEmpty String etagValue) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etagValue, blockAccountDto)) {
            return Response.status(406).build();
        }
        accountEndpoint.blockUser(blockAccountDto.getLogin(), blockAccountDto.getVersion());
        return Response.status(200).build();
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


    /**
     * Metoda odpowiedzialna za weryfikowanie konta
     *
     * @param accountVerificationDto obiekt dto przechowujący niezbędne dane do resetowania hasła
     * @return Odpowiedź serwera w postaci JSON
     */
    @PUT
    @Path("/account-verification")
    public Response accountVerification(@Valid AccountVerificationDto accountVerificationDto) {
        try {
            this.accountEndpoint.verifyAccount(accountVerificationDto);
        } catch (BaseAppException e) {
            Response.status(FORBIDDEN).entity(e.getMessage()).build(); // todo send key
        }
        return Response.ok().build(); // todo appropriate condition
    }
    /**
     * Zmień dane wybranego konta o poziomie dostępu klient
     *
     * @param otherClientChangeDataDto obiekt dto z nowymi danymi
     * @param etag Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
     * @return Odpowiedź serwera w postaci JSON
     */
    @PUT
    @Path("/changeOtherData/client")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ETagFilterBinding
    public Response changeOtherClientData(OtherClientChangeDataDto otherClientChangeDataDto, @HeaderParam("If-Match") String etag) {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, otherClientChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }

        try {
            OtherClientChangeDataDto account = accountEndpoint.changeOtherClientData(otherClientChangeDataDto);
            return Response.ok().entity(account).build();
        } catch (BaseAppException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        }


    }

    /**
     * Zmień dane wybranego konta o poziomie dostępu businnesWorker
     *
     * @param otherBusinessWorkerChangeDataDto obiekt dto z nowymi danymi
     * @param etag Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
     * @return Odpowiedź serwera w postaci JSON
     */
    @PUT
    @Path("/changeOtherData/businessworker")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ETagFilterBinding
    public Response changeOtherBusinessWorkerData(OtherBusinessWorkerChangeDataDto otherBusinessWorkerChangeDataDto, @HeaderParam("If-Match") String etag) {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, otherBusinessWorkerChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }

        try {
            OtherBusinessWorkerChangeDataDto account = accountEndpoint.changeOtherBusinessWorkerData(otherBusinessWorkerChangeDataDto);
            return Response.ok().entity(account).build();
        } catch (BaseAppException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Zmień dane wybranego konta o poziomie dostępu moderator lub administrator
     *
     * @param otherAccountChangeDataDto obiekt dto z nowymi danymi
     * @param etag Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
     * @return Odpowiedź serwera w postaci JSON
     */
    @PUT
    @Path("/changeOtherData")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ETagFilterBinding
    public Response changeOtherAccountData(OtherAccountChangeDataDto otherAccountChangeDataDto, @HeaderParam("If-Match") String etag) {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, otherAccountChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }

        try {
            AccountDto account = accountEndpoint.changeOtherAccountData(otherAccountChangeDataDto);
            return Response.ok().entity(account).build();
        } catch (BaseAppException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    /**
     * Zmień hasło według podanych w dto danych
     *
     * @param accountChangeEmailDto obiekt dto z loginem, nowym mailem oraz wersją
     */
    @PUT
    @Path("/change_email")
    @ETagFilterBinding
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeEmail(AccountChangeEmailDto accountChangeEmailDto, @HeaderParam("If-Match") String etag) {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, accountChangeEmailDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }

        try {
            accountEndpoint.changeEmail(accountChangeEmailDto);
        } catch (EJBException | OptimisticLockException e) {
            return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (BaseAppException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }

        return Response.noContent().build();
    }
}