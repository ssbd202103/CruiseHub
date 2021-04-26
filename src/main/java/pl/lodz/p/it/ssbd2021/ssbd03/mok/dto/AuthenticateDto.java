package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class AuthenticateDto {
    @Login
    private String login;

    @NotNull
    private String password;
}