package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;

import javax.ws.rs.core.Response;

public class AuthenticateResponse {
    @Getter
    @Setter
    private Response.Status responseStatus;

    @Getter
    @Setter
    private Account account;

    @Getter
    @Setter
    private String token;
}
