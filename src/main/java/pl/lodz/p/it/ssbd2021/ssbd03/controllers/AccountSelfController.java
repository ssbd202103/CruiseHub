package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.common.dto.MetadataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.ControllerException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountVerificationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.AccountEndpointLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.security.ETagFilterBinding;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;
import static pl.lodz.p.it.ssbd2021.ssbd03.utils.TransactionRepeater.tryAndRepeat;

@Path("/self")
@RequestScoped
public class AccountSelfController {

    @Inject
    private AccountEndpointLocal accountEndpoint;

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Pobiera szczegółowe informacje o koncie uwierzytelnionego użytkownika
     *
     * @return Reprezentacja szczegółów konta w postaci AcccountDetailsViewDto serializowanego na JSON
     */
    @GET
    @Path("/account-details")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSelfDetails() throws BaseAppException, JsonProcessingException {
        return mapper.writeValueAsString(tryAndRepeat(() -> accountEndpoint.getSelfDetails()));
    }

    /**
     * Zmienia hasło aktualnego użytkownika wedle danych podanych w dto, gdy operacja się powiedzie zwraca 204
     *
     * @param accountChangeOwnPasswordDto obiekt ktory przechowuje login, wersję, stare oraz nowe hasło podane przez użytkownika
     * @param etag                        Nagłówek If-Match żądania wymagany do potwierdzenia spójności danych
     */
    @ETagFilterBinding
    @PUT
    @Path("/change-password")
    @Consumes(MediaType.APPLICATION_JSON)
    public void changeOwnPassword(@NotNull(message = CONSTRAINT_NOT_NULL) @Valid AccountChangeOwnPasswordDto accountChangeOwnPasswordDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, accountChangeOwnPasswordDto)) {
            throw ControllerException.etagIdentityIntegrity();
        }
        tryAndRepeat(() -> accountEndpoint.changeOwnPassword(accountChangeOwnPasswordDto));
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
    public void changeClientData(ClientChangeDataDto clientChangeDataDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, clientChangeDataDto)) {
            throw ControllerException.etagIdentityIntegrity();
        }
        tryAndRepeat(() -> accountEndpoint.changeClientData(clientChangeDataDto));
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
    public void changeBusinessWorkerData(BusinessWorkerChangeDataDto businessWorkerChangeDataDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, businessWorkerChangeDataDto)) {
            throw ControllerException.etagIdentityIntegrity();
        }
        tryAndRepeat(() -> accountEndpoint.changeBusinessWorkerData(businessWorkerChangeDataDto));
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
    public void changeAdministratorData(AdministratorChangeDataDto administratorChangeDataDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, administratorChangeDataDto)) {
            throw ControllerException.etagIdentityIntegrity();
        }
        tryAndRepeat(() -> accountEndpoint.changeAdministratorData(administratorChangeDataDto));
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

    @PUT
    @Path("/change-theme-mode")
    @Consumes(MediaType.APPLICATION_JSON)
    @ETagFilterBinding
    public void changeMode(ChangeModeDto changeModeDto, @HeaderParam("If-Match") String etag) throws BaseAppException {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, changeModeDto)) {
            throw ControllerException.etagIdentityIntegrity();
        }
        tryAndRepeat(() -> accountEndpoint.changeMode(changeModeDto));
    }

    @GET
    @Path("/metadata")
    @Produces(MediaType.APPLICATION_JSON)
    public MetadataDto getSelfAccountMetadata() throws BaseAppException {
        return tryAndRepeat(() -> accountEndpoint.getSelfMetadata());
    }

    @GET
    @Path("/metadata/access-level/{access-level}")
    @Produces(MediaType.APPLICATION_JSON)
    public MetadataDto getSelfAccessLevelMetadata(@PathParam("access-level") String accessLevel) throws BaseAppException {
        AccessLevelType accessLevelType;
        try {
            accessLevelType = AccessLevelType.valueOf(accessLevel.toUpperCase().replace('-', '_'));
        } catch (IllegalArgumentException e) {
            throw new ControllerException(ACCESS_LEVEL_PARSE_ERROR);
        }
        return tryAndRepeat(() -> accountEndpoint.getSelfAccessLevelMetadata(accessLevelType));
    }

    @GET
    @Path("/metadata/address/")
    @Produces(MediaType.APPLICATION_JSON)
    public MetadataDto getSelfAddressMetadata() throws BaseAppException {
        return tryAndRepeat(() -> accountEndpoint.getSelfAddressMetadata());
    }

}
