package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
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
import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;
import static pl.lodz.p.it.ssbd2021.ssbd03.controllers.TransactionRepeater.tryAndRepeat;

/**
 * Klasa która udostępnia api RESTowe do wykonania operacji na kontach użytkowników, oraz zajmuje się walidacją danych.
 */
@Path("/account")
@RequestScoped
public class AccountController {
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
    public Response getAccountByLogin(@PathParam("login") @Login String login) throws BaseAppException {
        AccountDto account = tryAndRepeat(() -> accountEndpoint.getAccountByLogin(login));
        String ETag = EntityIdentitySignerVerifier.calculateEntitySignature(account);
        return Response.ok().entity(account).header("ETag", ETag).build();
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
    public AccountDetailsViewDto getAccountDetailsByLogin(@PathParam("login") String login) throws BaseAppException {
        return tryAndRepeat(() -> accountEndpoint.getAccountDetailsByLogin(login));
    }

    @GET
    @Path("/client/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientByLogin(@PathParam("login") @Login String login) throws BaseAppException {
        ClientDto client = tryAndRepeat(() -> accountEndpoint.getClientByLogin(login));
        String ETag = EntityIdentitySignerVerifier.calculateEntitySignature(client);
        return Response.ok().entity(client).header("ETag", ETag).build();
    }

    @GET
    @Path("/businessworker/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBusinessWorkerByLogin(@PathParam("login") @Login String login) throws BaseAppException {
        BusinessWorkerDto businessWorker = tryAndRepeat(() -> accountEndpoint.getBusinessWorkerByLogin(login));
        String ETag = EntityIdentitySignerVerifier.calculateEntitySignature(businessWorker);
        return Response.ok().entity(businessWorker).header("ETag", ETag).build();
    }

    @GET
    @Path("/moderator/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModeratorByLogin(@PathParam("login") @Login String login) throws BaseAppException {
        ModeratorDto moderator = tryAndRepeat(() -> accountEndpoint.getModeratorByLogin(login));
        String ETag = EntityIdentitySignerVerifier.calculateEntitySignature(moderator);
        return Response.ok().entity(moderator).header("ETag", ETag).build();
    }

    @GET
    @Path("/administrator/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdministratorByLogin(@PathParam("login") @Login String login) {
        try {
            AdministratorDto administrator = tryAndRepeat(() -> accountEndpoint.getAdministratorByLogin(login));
            String ETag = EntityIdentitySignerVerifier.calculateEntitySignature(administrator);
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
    public void createClient(@Valid @NotNull(message = CONSTRAINT_NOT_NULL) ClientForRegistrationDto clientForRegistrationDto) throws BaseAppException {
        tryAndRepeat(() -> accountEndpoint.createClientAccount(clientForRegistrationDto));
    }

    /**
     * Stwórz nowe konto z poziomem dostępu Pracownik firmy
     *
     * @param businessWorkerForRegistrationDto Zbiór danych niezbędnych dla stworzenia konta z poziomem dostępu Pracownik firmy
     */
    @POST
    @Path("/business-worker/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createBusinessWorker(@Valid @NotNull(message = CONSTRAINT_NOT_NULL) BusinessWorkerForRegistrationDto businessWorkerForRegistrationDto) throws BaseAppException {
        tryAndRepeat(() -> accountEndpoint.createBusinessWorkerAccount(businessWorkerForRegistrationDto));
    }

    /**
     * Pobierz informacje o wszystkich kontach
     *
     * @return Lista kont
     */
    @GET
    @Path("/accounts")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AccountDtoForList> getAllAccounts() throws BaseAppException {
        return tryAndRepeat(() -> accountEndpoint.getAllAccounts());
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
    public Response blockUser(BlockAccountDto blockAccountDto, @HeaderParam("If-Match") @NotNull(message = CONSTRAINT_NOT_NULL) @NotEmpty(message = CONSTRAINT_NOT_EMPTY) String etagValue) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etagValue, blockAccountDto)) {
            return Response.status(406).build();
        }
        tryAndRepeat(() -> accountEndpoint.blockUser(blockAccountDto.getLogin(), blockAccountDto.getVersion()));
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
    public AccountDto grantAccessLevel(GrantAccessLevelDto grantAccessLevel) throws BaseAppException {
        return accountEndpoint.grantAccessLevel(grantAccessLevel);
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
    public Response changeAccessLevelState(ChangeAccessLevelStateDto changeAccessLevelStateDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, changeAccessLevelStateDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }
        AccountDto account = accountEndpoint.changeAccessLevelState(changeAccessLevelStateDto);
        return Response.ok().entity(account).build();
    }


    /**
     * Metoda odpowiedzialna za resetowanie hasła
     *
     * @param passwordResetDto obiekt dto przechowujący niezbędne dane do resetowania hasła
     * @return Odpowiedź serwera w postaci JSON
     */
    @PUT
    @Path("/reset-password")
    public void resetPassword(@NotNull(message = CONSTRAINT_NOT_NULL) @Valid PasswordResetDto passwordResetDto) throws BaseAppException {
        this.accountEndpoint.resetPassword(passwordResetDto);
    }

    /**
     * Metoda odpowiedzialna za zgłoszenia życzenia resetowania hasła
     *
     * @param login login użytkownika
     * @return Odpowiedź serwera w postaci JSON
     */
    @POST
    @Path("/request-password-reset/{login}")
    public void requestPasswordReset(@PathParam("login") @Login String login) throws BaseAppException {
        this.accountEndpoint.requestPasswordReset(login);
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
    public Response unblockUser(UnblockAccountDto unblockAccountDto, @HeaderParam("If-Match") @NotNull(message = CONSTRAINT_NOT_NULL) @NotEmpty(message = CONSTRAINT_NOT_EMPTY) String tagValue) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(tagValue, unblockAccountDto)) {
            return Response.status(406).build();
        }
        accountEndpoint.unblockUser(unblockAccountDto.getLogin(), unblockAccountDto.getVersion());
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
    public Response changeOwnPassword(@NotNull(message = CONSTRAINT_NOT_NULL) @Valid AccountChangeOwnPasswordDto accountChangeOwnPasswordDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, accountChangeOwnPasswordDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }
        accountEndpoint.changeOwnPassword(accountChangeOwnPasswordDto);
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
    public void requestSomeonesPasswordReset(@PathParam("login") @Login String login, @PathParam("email") @Email(message = REGEX_INVALID_EMAIL) @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
            String email) throws BaseAppException {
        this.accountEndpoint.requestSomeonesPasswordReset(login, email);
    }


    /**
     * Metoda odpowiedzialna za weryfikowanie konta
     *
     * @param accountVerificationDto obiekt dto przechowujący niezbędne dane do resetowania hasła
     * @return Odpowiedź serwera w postaci JSON
     */
    @PUT
    @Path("/account-verification")
    public void accountVerification(@NotNull(message = CONSTRAINT_NOT_NULL) @Valid AccountVerificationDto accountVerificationDto) throws BaseAppException {
        this.accountEndpoint.verifyAccount(accountVerificationDto);
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
    public Response changeOtherClientData(OtherClientChangeDataDto otherClientChangeDataDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, otherClientChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }
        OtherClientChangeDataDto account = accountEndpoint.changeOtherClientData(otherClientChangeDataDto);
        return Response.ok().entity(account).build();
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
    public Response changeOtherBusinessWorkerData(OtherBusinessWorkerChangeDataDto otherBusinessWorkerChangeDataDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, otherBusinessWorkerChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }
        OtherBusinessWorkerChangeDataDto account = accountEndpoint.changeOtherBusinessWorkerData(otherBusinessWorkerChangeDataDto);
        return Response.ok().entity(account).build();
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
    public Response changeOtherAccountData(OtherAccountChangeDataDto otherAccountChangeDataDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, otherAccountChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }

        AccountDto account = accountEndpoint.changeOtherAccountData(otherAccountChangeDataDto);
        return Response.ok().entity(account).build();
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
    public Response changeEmail(AccountChangeEmailDto accountChangeEmailDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, accountChangeEmailDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }
        accountEndpoint.changeEmail(accountChangeEmailDto);
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
    public Response changeClientData(ClientChangeDataDto clientChangeDataDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, clientChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }
        accountEndpoint.changeClientData(clientChangeDataDto);
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
    public Response changeBusinessWorkerData(BusinessWorkerChangeDataDto businessWorkerChangeDataDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, businessWorkerChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }
        accountEndpoint.changeBusinessWorkerData(businessWorkerChangeDataDto);
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
    public Response changeModeratorData(ModeratorChangeDataDto moderatorChangeDataDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, moderatorChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }
        accountEndpoint.changeModeratorData(moderatorChangeDataDto);
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
    public Response changeAdministratorData(AdministratorChangeDataDto administratorChangeDataDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, administratorChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }
        accountEndpoint.changeAdministratorData(administratorChangeDataDto);
        return Response.noContent().build();
    }

    @PUT
    @Path("/change_mode")
    @Consumes(MediaType.APPLICATION_JSON)
    @ETagFilterBinding
    public Response changeMode(ChangeModeDto changeModeDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, changeModeDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }
        accountEndpoint.changeMode(changeModeDto);
        return Response.noContent().build();
    }
}