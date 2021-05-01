package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetDto {
    private String token;
    @Login
    private String login;
    private String password;
}
