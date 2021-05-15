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
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BusinessWorkerDto implements SignableEntity {
    @Login
    private String login;

    @Name
    private String firstName;

    @Name
    private String secondName;

    @Email
    private String email;

    @NotNull
    private LanguageType languageType;

    @PhoneNumber
    private String phoneNumber;

    @PositiveOrZero
    private Long version;

    @Override
    @JsonIgnore
    public String getSignablePayload() {
        return login + '.' + version;
    }
}
