package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AuthenticateDto {
    @NotNull
    @Login
    private String login;

    @NotNull
    private String password;

    public Credential toCredential() {
        return new UsernamePasswordCredential(login, password);
    }
}