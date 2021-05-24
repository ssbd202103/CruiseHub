package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.CompanyName;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BusinessWorkerWithCompanyDto implements SignableEntity {
    @Login
    private String login;

    @Name
    private String firstName;

    @Name
    private String secondName;

    @Email(message = REGEX_INVALID_EMAIL)
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String email;

    @NotNull(message = CONSTRAINT_NOT_NULL)
    private LanguageType languageType;

    @PhoneNumber
    private String phoneNumber;

    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO_ERROR)
    private long version;

    @CompanyName
    private String companyName;

    @PhoneNumber
    private String companyPhoneNumber;



    @Override
    @JsonIgnore
    public String getSignablePayload() {
        return login + '.' + version;
    }
}
