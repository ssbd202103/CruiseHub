package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Password;

import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AuthenticateDto {
    @Login
    private String login;
    private String password;

    public Credential toCredential() {
        return new UsernamePasswordCredential(login, password);
    }
}