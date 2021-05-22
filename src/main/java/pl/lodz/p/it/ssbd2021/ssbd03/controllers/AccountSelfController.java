package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccountDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.AccountEndpointLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.security.ETagFilterBinding;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.ETAG_IDENTITY_INTEGRITY_ERROR;
import static pl.lodz.p.it.ssbd2021.ssbd03.controllers.TransactionRepeater.tryAndRepeat;

@Path("/self")
@RequestScoped
public class AccountSelfController {

    @Inject
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

    /**
     * Zmienia hasło aktualnego użytkownika wedle danych podanych w dto, gdy operacja się powiedzie zwraca 204
     *
     * @param accountChangeOwnPasswordDto obiekt ktory przechowuje login, wersję, stare oraz nowe hasło podane przez użytkownika
     * @param etag                        Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
     * @return Zwraca kod kod potwierdzający poprawne bądź nieporawne wykonanie
     */
    @ETagFilterBinding
    @PUT
    @Path("/change-password")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeOwnPassword(@NotNull(message = CONSTRAINT_NOT_NULL) @Valid AccountChangeOwnPasswordDto accountChangeOwnPasswordDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, accountChangeOwnPasswordDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }
        tryAndRepeat(() -> accountEndpoint.changeOwnPassword(accountChangeOwnPasswordDto));
        return Response.noContent().build();
    }


    /**
     * Zmień dane konta klienta
     *
     * @param clientChangeDataDto obiekt dto z nowymi danymi
     */
    @PUT
    @Path("/change-client-data")
    @Consumes(MediaType.APPLICATION_JSON)
    @ETagFilterBinding
    public Response changeClientData(ClientChangeDataDto clientChangeDataDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, clientChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }
        tryAndRepeat(() -> accountEndpoint.changeClientData(clientChangeDataDto));
        return Response.noContent().build();
    }

    /**
     * Zmień dane konta pracownika firmy
     *
     * @param businessWorkerChangeDataDto obiekt dto z nowymi danymi
     */
    @PUT
    @Path("/change-business-worker-data")
    @Consumes(MediaType.APPLICATION_JSON)
    @ETagFilterBinding
    public Response changeBusinessWorkerData(BusinessWorkerChangeDataDto businessWorkerChangeDataDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, businessWorkerChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }
        tryAndRepeat(() -> accountEndpoint.changeBusinessWorkerData(businessWorkerChangeDataDto));
        return Response.noContent().build();
    }

    /**
     * Zmień dane konta moderatora
     *
     * @param moderatorChangeDataDto obiekt dto z nowymi danymi
     */
    @PUT
    @Path("/change-moderator-data")
    @Consumes(MediaType.APPLICATION_JSON)
    @ETagFilterBinding
    public Response changeModeratorData(ModeratorChangeDataDto moderatorChangeDataDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, moderatorChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }
        tryAndRepeat(() -> accountEndpoint.changeModeratorData(moderatorChangeDataDto));
        return Response.noContent().build();
    }

    /**
     * Zmień dane konta administratora
     *
     * @param administratorChangeDataDto obiekt dto z nowymi danymi
     */
    @PUT
    @Path("/change-administrator-data")
    @Consumes(MediaType.APPLICATION_JSON)
    @ETagFilterBinding
    public Response changeAdministratorData(AdministratorChangeDataDto administratorChangeDataDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, administratorChangeDataDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }
        tryAndRepeat(() -> accountEndpoint.changeAdministratorData(administratorChangeDataDto));
        return Response.noContent().build();
    }

    /**
     * Zmień mail według podanych w dto danych
     *
     * @param accountChangeEmailDto obiekt dto z loginem, nowym mailem oraz wersją
     */
    @PUT // todo read login from security context
    @Path("/change-email")
    @ETagFilterBinding
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeEmail(AccountChangeEmailDto accountChangeEmailDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, accountChangeEmailDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }
        tryAndRepeat(() -> accountEndpoint.changeEmail(accountChangeEmailDto));
        return Response.noContent().build();
    }

    @PUT
    @Path("/change-theme-mode")
    @Consumes(MediaType.APPLICATION_JSON)
    @ETagFilterBinding
    public Response changeMode(ChangeModeDto changeModeDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, changeModeDto)) {
            return Response.status(NOT_ACCEPTABLE).entity(ETAG_IDENTITY_INTEGRITY_ERROR).build();
        }
        tryAndRepeat(() -> accountEndpoint.changeMode(changeModeDto));
        return Response.noContent().build();
    }


}
