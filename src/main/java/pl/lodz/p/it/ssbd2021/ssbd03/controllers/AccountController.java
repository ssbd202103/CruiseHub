package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.common.dto.MetadataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.ControllerException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.AccountChangeEmailDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.OtherAccountChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.OtherBusinessWorkerChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.OtherClientChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.ChangeAccessLevelStateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.GrantAccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.AccountEndpointLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.security.ETagFilterBinding;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;
import static pl.lodz.p.it.ssbd2021.ssbd03.utils.TransactionRepeater.tryAndRepeat;

/**
 * Klasa która udostępnia api RESTowe do wykonania operacji na kontach użytkowników, oraz zajmuje się walidacją danych.
 */
@Path("/account")
@RequestScoped
public class AccountController {
    @Inject
    private AccountEndpointLocal accountEndpoint;

    private final ObjectMapper mapper = new ObjectMapper(); //todo remove it when stable and consistent Jackson mapper settings are provided

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
    public Response getAccountByLogin(@PathParam("login") @Login @Valid String login) throws BaseAppException {
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
    @Path("/details/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAccountDetailsByLogin(@PathParam("login") @Valid @Login String login) throws BaseAppException, JsonProcessingException {
        return mapper.writeValueAsString(tryAndRepeat(() -> accountEndpoint.getAccountDetailsByLogin(login)));
    }

    @GET
    @Path("/client/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientByLogin(@PathParam("login") @Valid @Login String login) throws BaseAppException {
        ClientDto client = tryAndRepeat(() -> accountEndpoint.getClientByLogin(login));
        String ETag = EntityIdentitySignerVerifier.calculateEntitySignature(client);
        return Response.ok().entity(client).header("ETag", ETag).build();
    }

    @GET
    @Path("/business-worker/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBusinessWorkerByLogin(@PathParam("login") @Valid @Login String login) throws BaseAppException {
        BusinessWorkerDto businessWorker = tryAndRepeat(() -> accountEndpoint.getBusinessWorkerByLogin(login));
        String ETag = EntityIdentitySignerVerifier.calculateEntitySignature(businessWorker);
        return Response.ok().entity(businessWorker).header("ETag", ETag).build();
    }

    @GET
    @Path("/moderator/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModeratorByLogin(@PathParam("login") @Valid @Login String login) throws BaseAppException {
        ModeratorDto moderator = tryAndRepeat(() -> accountEndpoint.getModeratorByLogin(login));
        String ETag = EntityIdentitySignerVerifier.calculateEntitySignature(moderator);
        return Response.ok().entity(moderator).header("ETag", ETag).build();
    }

    @GET
    @Path("/administrator/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdministratorByLogin(@PathParam("login") @Valid @Login String login) throws BaseAppException {
        AdministratorDto administrator = tryAndRepeat(() -> accountEndpoint.getAdministratorByLogin(login));
        String ETag = EntityIdentitySignerVerifier.calculateEntitySignature(administrator);
        return Response.ok().entity(administrator).header("ETag", ETag).build();
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
     * Pobiera listę wszystkich niezatwierdzonych pracowników firm
     *
     * @return lista dto z pracownikami firm
     * @throws BaseAppException bazowy wyjątek aplikacji
     */
    @GET
    @Path("/unconfirmed-business-workers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BusinessWorkerWithCompanyDto> getAllUnconfirmedBusinessWorkers() throws BaseAppException {
        return tryAndRepeat(() -> accountEndpoint.getAllUnconfirmedBusinessWorkers());
    }

    /**
     * Metoda pośrednio odpowiedzialna za blokowanie użytkownika
     *
     * @param blockAccountDto Obiekt z loginem użytkownika do zablokowania oraz wersją
     * @param etag            Wartość etaga
     */
    @ETagFilterBinding
    @PUT
    @Path("/block")
    @Consumes(MediaType.APPLICATION_JSON)
    public void blockUser(@Valid BlockAccountDto blockAccountDto, @HeaderParam("If-Match") @NotNull(message = CONSTRAINT_NOT_NULL) @NotEmpty(message = CONSTRAINT_NOT_EMPTY) @Valid String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, blockAccountDto)) {
            throw ControllerException.etagIdentityIntegrity();
        }
        tryAndRepeat(() -> accountEndpoint.blockUser(blockAccountDto.getLogin(), blockAccountDto.getVersion()));
    }

    /**
     * @param unblockAccountDto Obiekt posiadający login użytkownika którego mamy odblokować
     * @param etag              Wartość etaga
     */
    @ETagFilterBinding
    @PUT
    @Path("/unblock")
    @Consumes(MediaType.APPLICATION_JSON)
    public void unblockUser(@Valid UnblockAccountDto unblockAccountDto, @HeaderParam("If-Match") @NotNull(message = CONSTRAINT_NOT_NULL) @NotEmpty(message = CONSTRAINT_NOT_EMPTY) String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, unblockAccountDto)) {
            throw ControllerException.etagIdentityIntegrity();
        }
        tryAndRepeat(() -> accountEndpoint.unblockUser(unblockAccountDto.getLogin(), unblockAccountDto.getVersion()));
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
    public AccountDto grantAccessLevel(@Valid GrantAccessLevelDto grantAccessLevel, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, grantAccessLevel)) {
            throw ControllerException.etagIdentityIntegrity();
        }
        return tryAndRepeat(() -> accountEndpoint.grantAccessLevel(grantAccessLevel));
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
    public AccountDto changeAccessLevelState(@Valid ChangeAccessLevelStateDto changeAccessLevelStateDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, changeAccessLevelStateDto)) {
            throw ControllerException.etagIdentityIntegrity();
        }
        return tryAndRepeat(() -> accountEndpoint.changeAccessLevelState(changeAccessLevelStateDto));
    }


    /**
     * Metoda odpowiedzialna za resetowanie hasła
     *
     * @param passwordResetDto obiekt dto przechowujący niezbędne dane do resetowania hasła
     */
    @PUT
    @Path("/reset-password")
    public void resetPassword(@NotNull(message = CONSTRAINT_NOT_NULL) @Valid PasswordResetDto passwordResetDto) throws BaseAppException {
        tryAndRepeat(() -> this.accountEndpoint.resetPassword(passwordResetDto));
    }

    /**
     * Metoda odpowiedzialna za zgłoszenia życzenia resetowania hasła
     *
     * @param login login użytkownika
     */
    @POST
    @Path("/request-password-reset/{login}")
    public void requestPasswordReset(@PathParam("login") @Valid @Login String login) throws BaseAppException {
        tryAndRepeat(() -> this.accountEndpoint.requestPasswordReset(login));
    }

    /**
     * Metoda odpowiedzialna za zgłoszenia życzenia resetowania hasła dla danego użytkownika
     *
     * @param login login użytkownika
     * @param email e-mail użytkownika
     */
    @POST
    @Path("/request-someones-password-reset/{login}/{email}")
    public void requestSomeonesPasswordReset(@PathParam("login") @Valid @Login String login, @PathParam("email")
    @Valid @Email(message = REGEX_INVALID_EMAIL) @NotEmpty(message = CONSTRAINT_NOT_EMPTY) String email) throws BaseAppException {
        tryAndRepeat(() -> this.accountEndpoint.requestSomeonesPasswordReset(login, email));
    }


    /**
     * Metoda odpowiedzialna za weryfikowanie konta
     *
     * @param accountVerificationDto obiekt dto przechowujący niezbędne dane do resetowania hasła
     */
    @PUT
    @Path("/verify")
    public void verifyAccount(@NotNull(message = CONSTRAINT_NOT_NULL) @Valid AccountVerificationDto accountVerificationDto) throws BaseAppException {
        tryAndRepeat(() -> this.accountEndpoint.verifyAccount(accountVerificationDto));
    }

    /**
     * Zmień dane wybranego konta o poziomie dostępu klient
     *
     * @param otherClientChangeDataDto obiekt dto z nowymi danymi
     * @param etag                     Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
     * @return Odpowiedź serwera w postaci JSON
     */
    @PUT
    @Path("/change-client-data")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ETagFilterBinding
    public OtherClientChangeDataDto changeOtherClientData(@NotNull(message = CONSTRAINT_NOT_NULL) @Valid OtherClientChangeDataDto otherClientChangeDataDto,
                                                          @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, otherClientChangeDataDto)) {
            throw ControllerException.etagIdentityIntegrity();
        }

        return tryAndRepeat(() -> accountEndpoint.changeOtherClientData(otherClientChangeDataDto));
    }

    /**
     * Zmień dane wybranego konta o poziomie dostępu businnesWorker
     *
     * @param otherBusinessWorkerChangeDataDto obiekt dto z nowymi danymi
     * @param etag                             Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
     * @return Odpowiedź serwera w postaci JSON
     */
    @PUT
    @Path("/change-business-worker-data")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ETagFilterBinding
    public OtherBusinessWorkerChangeDataDto changeOtherBusinessWorkerData(@NotNull(message = CONSTRAINT_NOT_NULL) @Valid OtherBusinessWorkerChangeDataDto otherBusinessWorkerChangeDataDto,
                                                                          @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, otherBusinessWorkerChangeDataDto)) {
            throw ControllerException.etagIdentityIntegrity();
        }

        return tryAndRepeat(() -> accountEndpoint.changeOtherBusinessWorkerData(otherBusinessWorkerChangeDataDto));
    }

    /**
     * Zmień dane wybranego konta o poziomie dostępu moderator lub administrator
     *
     * @param otherAccountChangeDataDto obiekt dto z nowymi danymi
     * @param etag                      Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
     * @return Odpowiedź serwera w postaci JSON
     */
    @PUT
    @Path("/change-account-data")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ETagFilterBinding
    public AccountDto changeOtherAccountData(@NotNull(message = CONSTRAINT_NOT_NULL) @Valid OtherAccountChangeDataDto otherAccountChangeDataDto,
                                             @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, otherAccountChangeDataDto)) {
            throw ControllerException.etagIdentityIntegrity();
        }

        return tryAndRepeat(() -> accountEndpoint.changeOtherAccountData(otherAccountChangeDataDto));
    }

    /**
     * Zmień mail według podanych w dto danych
     *
     * @param accountVerificationDto obiekt dto z tokenem
     */
    @PUT
    @Path("/change-email")
    @Consumes(MediaType.APPLICATION_JSON)
    public void changeEmail(@NotNull(message = CONSTRAINT_NOT_NULL) @Valid AccountVerificationDto accountVerificationDto) throws BaseAppException {
        tryAndRepeat(() -> this.accountEndpoint.changeEmail(accountVerificationDto));
    }

    /**
     * Potwierdz danego pracownika firmy
     *
     * @param blockAccountDto dto zawierające wersje oraz login danego pracownika
     * @param etag            Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
     * @throws BaseAppException bazowy wyjątek apklikacji
     */
    @PUT
    @Path("/confirm-business-worker")
    @ETagFilterBinding
    @Consumes(MediaType.APPLICATION_JSON)
    public void confirmBusinessWorker(BlockAccountDto blockAccountDto, @HeaderParam("If-Match") @NotNull(message = CONSTRAINT_NOT_NULL) @NotEmpty(message = CONSTRAINT_NOT_EMPTY) String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, blockAccountDto)) {
            throw ControllerException.etagIdentityIntegrity();
        }
        tryAndRepeat(() -> accountEndpoint.confirmBusinessWorker(blockAccountDto));
    }

    @POST
    @Path("/request-email-change")
    public void requestEmailChange(AccountChangeEmailDto accountChangeEmailDto) throws BaseAppException {
        tryAndRepeat(() -> accountEndpoint.requestEmailChange(accountChangeEmailDto));
    }

    @POST
    @Path("/request-other-email-change")
    public void requestOtherEmailChange(AccountChangeEmailDto accountChangeEmailDto) throws BaseAppException {
        tryAndRepeat(() -> accountEndpoint.requestOtherEmailChange(accountChangeEmailDto));
    }


    /**
     * Pobiera metadane użytkownika
     *
     * @param login Login użytkownika
     * @return Postać DTO metadanych
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @GET
    @Path("/metadata/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountMetadataDto getAccountMetadata(@PathParam("login") String login) throws BaseAppException {
        return tryAndRepeat(() -> accountEndpoint.getAccountMetadata(login));
    }


    /**
     * Pobiera metadane wybranego poziomu dostępu użytkownika
     *
     * @param login       Login użytkownika
     * @param accessLevel Wybrany poziom dostępu
     * @return Reprezentacja DTO metadanych
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @GET
    @Path("/metadata/access-level/{access-level}/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public MetadataDto getAccountAccessLevelMetadata(@PathParam("login") String login,
                                                     @PathParam("access-level") String accessLevel) throws BaseAppException {
        AccessLevelType accessLevelType;
        try {
            accessLevelType = AccessLevelType.valueOf(accessLevel.toUpperCase().replace('-', '_'));
        } catch (IllegalArgumentException e) {
            throw new ControllerException(ACCESS_LEVEL_PARSE_ERROR);
        }
        return tryAndRepeat(() -> accountEndpoint.getAccessLevelMetadata(login, accessLevelType));
    }


    /**
     * Pobiera metadane adresu danego użytkownika
     *
     * @param login Login użytkownika
     * @return Reprezentacja DTO metadanych
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    @GET
    @Path("/metadata/address/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public MetadataDto getClientAddressMetadata(@PathParam("login") String login) throws BaseAppException {
        return tryAndRepeat(() -> accountEndpoint.getAddressMetadata(login));
    }
}