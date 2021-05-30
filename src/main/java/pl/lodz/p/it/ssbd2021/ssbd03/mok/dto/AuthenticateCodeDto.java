package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AuthenticateCodeDto {
    private String login;
    private String code;
}