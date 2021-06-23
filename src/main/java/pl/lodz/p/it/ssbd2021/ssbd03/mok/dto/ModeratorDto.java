package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.FirstName;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.SecondName;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ModeratorDto implements SignableEntity {
    @Login
    private String login;

    @FirstName
    private String firstName;

    @SecondName
    private String secondName;

    @Email(message = REGEX_INVALID_EMAIL)
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String email;

    @NotNull(message = CONSTRAINT_NOT_NULL)
    private LanguageType languageType;

    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO)
    private long version;

    @Override
    @JsonIgnore
    public String getSignablePayload() {
        return login + '.' + version;
    }
}
