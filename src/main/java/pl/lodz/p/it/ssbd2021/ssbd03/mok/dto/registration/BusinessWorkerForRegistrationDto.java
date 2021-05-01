package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessWorkerForRegistrationDto {

    @Name
    private String firstName;

    @Name
    private String secondName;

    @Login
    private String login;

    @Email
    private String email;

    @NotEmpty
    private String password;

    @NotNull
    private LanguageType languageType;

    @PhoneNumber
    private String phoneNumber;

    @NotEmpty
    private String companyName;
}
