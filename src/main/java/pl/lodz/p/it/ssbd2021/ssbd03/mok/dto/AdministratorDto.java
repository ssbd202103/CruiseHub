package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdministratorDto implements SignableEntity {
    @Login
    private String login;

    @Name
    private String firstName;

    @Name
    private String secondName;

    @Email(message = REGEX_INVALID_EMAIL)
    private String email;

    @NotNull(message = CONSTRAINT_NOT_NULL)
    private LanguageType languageType;

    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO_ERROR)
    private Long version;

    @Override
    @JsonIgnore
    public String getSignablePayload() {
        return login + '.' + version;
    }
}
