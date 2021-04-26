package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientForRegistrationDto {

    @NotNull
    @NotEmpty
    @Name
    private String firstName;

    @NotNull
    @NotEmpty
    @Name
    private String secondName;

    @NotNull
    @NotEmpty
    @Login
    private String login;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    private LanguageType languageType;

    @NotNull
    private AddressDto addressDto;

    @NotNull
    @NotEmpty
    @PhoneNumber
    private String phoneNumber;

}
