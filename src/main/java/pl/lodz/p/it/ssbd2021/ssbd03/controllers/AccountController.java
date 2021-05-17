package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.*;
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
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.*;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

/**
 * Klasa która udostępnia api RESTowe do wykonania operacji na kontach użytkowników, oraz zajmuje się walidacją danych.
 */
@Path("/account")
@RequestScoped
public class AccountController {

    private final ObjectMapper mapper = new ObjectMapper(); //Jackson is set as default serializer,
    // but Payara likes to ignore it for whatever reason

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
            return Response.status(INTERNAL_SERVER_ERROR).entity(SERIALIZATION_PARSING_ERROR).build();
        }
    }

    @GET
    @Path("/client/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientByLogin(@PathParam("login") @Login String login) {
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
    public Response getBusinessWorkerByLogin(@PathParam("login") @Login String login) {
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
    public Response getModeratorByLogin(@PathParam("login") @Login String login) {
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
    public Response getAdministratorByLogin(@PathParam("login") @Login String login) {
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
     * @param clientForRegistrationDto Zbiór danych niezbędnych dla stworzenia konta z poziomem dostępu Klient
     */
    @POST
    @Path("/client/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createClient(@Valid @NotNull ClientForRegistrationDto clientForRegistrationDto) throws BaseAppException {
        accountEndpoint.createClientAccount(clientForRegistrationDto);
        //todo handle exceptions
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
        //todo handle exceptions
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
     *
     * @param blockAccountDto Obiekt z loginem użytkownika do zablokowania oraz wersją
     * @param etagValue       Wartość etaga
     * @return Respons wraz z kodem
     */
    @ETagFilterBinding
    @PUT
    @Path("/block")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response blockUser(BlockAccountDto blockAccountDto, @HeaderParam("If-Match") @NotNull @NotEmpty String etagValue) {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etagValue, blockAccountDto)) {
            return Response.status(406).build();
        }
        try {
            accountEndpoint.blockUser(blockAccountDto.getLogin(), blockAccountDto.getVersion());
        } catch (BaseAppException e) {
            return Response.status(FORBIDDEN).entity(e.getMessage()).build();
        }

        return Response.ok().build();
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
        } catch (FacadeException e) {
            return Response.status(FORBIDDEN).entity(e.getMessage()).build();
        } catch (BaseAppException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Zmień stan danego poziomu dostępu (włącz/wyłącz)
     *
     * @param changeAccessLevelStateDto Obiekt przesyłowy danych potrzebnych do zmiany stanu poziomu dostępu
     * @param etag                      Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
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
        } catch (FacadeException e) {
            return Response.status(FORBIDDEN).entity(e.getMessage()).build();
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
        } catch (FacadeException e) {
            return Response.status(FORBIDDEN).entity(e.getMessage()).build();
        } catch (BaseAppException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build(); // todo send key
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
     * @param unblockAccountDto Obiekt posiadający login użytkownika którego mamy odblokować
     * @param tagValue          Wartość etaga
     * @return Zwraca kod potwierdzający poprawne bądź niepoprawne wykonanie
     */
    @ETagFilterBinding
    @PUT
    @Path("/unblock")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response unblockUser(UnblockAccountDto unblockAccountDto, @HeaderParam("If-Match") @NotNull @NotEmpty String tagValue) {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(tagValue, unblockAccountDto)) {
            return Response.status(406).build();
        }
        try {
            accountEndpoint.unblockUser(unblockAccountDto.getLogin(), unblockAccountDto.getVersion());
        } catch (BaseAppException e) {
            return Response.status(FORBIDDEN).entity(e.getMessage()).build();
        }


        return Response.status(200).build();
    }

    /**
     * Zmienia hasło aktualnego użytkownika wedle danych podanych w dto, gdy operacja się powiedzie zwraca 204
     *
     * @param accountChangeOwnPasswordDto obiekt ktory przechowuje login, wersję, stare oraz nowe hasło podane przez użytkownika
     * @param etag                        Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
     * @return Zwraca kod kod potwierdzający poprawne bądź nieporawne wykonanie
     */
    @ETagFilterBinding
    @PUT
    @Path("/change_own_password")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeOwnPassword(@NotNull @Valid AccountChangeOwnPasswordDto accountChangeOwnPasswordDto, @HeaderParam("If-Match") String etag) {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, accountChangeOwnPasswordDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }

        try {
            accountEndpoint.changeOwnPassword(accountChangeOwnPasswordDto);
        } catch (FacadeException e) {
            return Response.status(FORBIDDEN).entity(e.getMessage()).build();
        } catch (EJBException e) {
            return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (BaseAppException e) {
            if (e.getMessage().equals(OPTIMISTIC_LOCK_EXCEPTION)) {
                return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
            }
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }

        return Response.noContent().build();
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
        } catch (FacadeException e) {
            return Response.status(FORBIDDEN).entity(e.getMessage()).build();
        } catch (BaseAppException e) {
            Response.status(FORBIDDEN).entity(e.getMessage()).build(); // todo send key
        }
        return Response.ok().build(); // todo appropriate condition
    }

    /**
     * Zmień dane wybranego konta o poziomie dostępu klient
     *
     * @param otherClientChangeDataDto obiekt dto z nowymi danymi
     * @param etag                     Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
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
        } catch (FacadeException e) {
            return Response.status(FORBIDDEN).entity(e.getMessage()).build();
        } catch (BaseAppException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        }


    }

    /**
     * Zmień dane wybranego konta o poziomie dostępu businnesWorker
     *
     * @param otherBusinessWorkerChangeDataDto obiekt dto z nowymi danymi
     * @param etag                             Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
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
        } catch (FacadeException e) {
            return Response.status(FORBIDDEN).entity(e.getMessage()).build();
        } catch (BaseAppException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Zmień dane wybranego konta o poziomie dostępu moderator lub administrator
     *
     * @param otherAccountChangeDataDto obiekt dto z nowymi danymi
     * @param etag                      Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
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
        } catch (FacadeException e) {
            return Response.status(FORBIDDEN).entity(e.getMessage()).build();
        } catch (BaseAppException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Zmień mail według podanych w dto danych
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
        } catch (FacadeException e) {
            return Response.status(FORBIDDEN).entity(e.getMessage()).build();
        } catch (EJBException e) {
            return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (BaseAppException e) {
            if (e.getMessage().equals(OPTIMISTIC_LOCK_EXCEPTION)) {
                return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
            }
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }

        return Response.noContent().build();
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
        } catch (FacadeException e) {
            return Response.status(FORBIDDEN).entity(e.getMessage()).build();
        } catch (EJBException e) {
            return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (BaseAppException e) {
            if (e.getMessage().equals(OPTIMISTIC_LOCK_EXCEPTION)) {
                return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
            }
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
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
        } catch (FacadeException e) {
            return Response.status(FORBIDDEN).entity(e.getMessage()).build();
        } catch (EJBException e) {
            return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (BaseAppException e) {
            if (e.getMessage().equals(OPTIMISTIC_LOCK_EXCEPTION)) {
                return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
            }
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
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
    public Response changeModeratorData(ModeratorChangeDataDto moderatorChangeDataDto, @HeaderParam("If-Match") String etag) {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, moderatorChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }

        try {
            accountEndpoint.changeModeratorData(moderatorChangeDataDto);
        } catch (FacadeException e) {
            return Response.status(FORBIDDEN).entity(e.getMessage()).build();
        } catch (EJBException e) {
            return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (BaseAppException e) {
            if (e.getMessage().equals(OPTIMISTIC_LOCK_EXCEPTION)) {
                return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
            }
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
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
        } catch (FacadeException e) {
            return Response.status(FORBIDDEN).entity(e.getMessage()).build();
        } catch (EJBException e) {
            return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (BaseAppException e) {
            if (e.getMessage().equals(OPTIMISTIC_LOCK_EXCEPTION)) {
                return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
            }
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }

        return Response.noContent().build();
    }
}