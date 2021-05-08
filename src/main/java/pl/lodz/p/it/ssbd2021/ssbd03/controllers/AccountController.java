package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDtoForList;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.BlockAccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.GrantAccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.AccountEndpointLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.security.ETagFilterBinding;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.util.List;


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
     * Pobierz informacje o wszystkich kontach
     *
     * @return lista kont
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
     * @return Odpowiedź serwera w postaci JSON
     */
    @ETagFilterBinding
    @PUT
    @Path("/grantAccessLevel")
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
}