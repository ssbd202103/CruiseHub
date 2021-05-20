package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Password;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.REGEX_INVALID_EMAIL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientForRegistrationDto {

    @Name
    private String firstName;

    @Name
    private String secondName;

    @Login
    private String login;

    @Email(message = REGEX_INVALID_EMAIL)
    private String email;

    @Password
    private String password;

    @NotNull(message = CONSTRAINT_NOT_NULL)
    private LanguageType languageType;

    @Valid
    private AddressDto addressDto;

    @PhoneNumber
    private String phoneNumber;
}
