package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AuthenticateDto {
    private String login;
    private String password;

    private Boolean darkMode;

    private String language;

    public Credential toCredential() {
        return new UsernamePasswordCredential(login, password);
    }
}