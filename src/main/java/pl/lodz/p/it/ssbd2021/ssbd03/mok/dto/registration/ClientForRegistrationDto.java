package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientForRegistrationDto {

    @FirstName
    private String firstName;

    @SecondName
    private String secondName;

    @Login
    private String login;

    @Email(message = REGEX_INVALID_EMAIL)
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String email;

    @Password
    @ToString.Exclude
    private String password;

    @NotNull(message = CONSTRAINT_NOT_NULL)
    private LanguageType languageType;

    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    private AddressDto addressDto;

    @PhoneNumber
    private String phoneNumber;
}
