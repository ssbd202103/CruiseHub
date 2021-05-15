package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Password;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModeratorForRegistrationDto {

    @Name
    private String firstName;

    @Name
    private String secondName;

    @Login
    private String login;

    @Email
    private String email;

    @Password
    private String password;

    @NotNull
    private LanguageType languageType;
}
