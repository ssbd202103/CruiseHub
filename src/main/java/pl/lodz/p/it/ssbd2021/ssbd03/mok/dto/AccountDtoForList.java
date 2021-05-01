package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDtoForList {

    @Login
    private String login;


    @Email
    private String email;

    @NotNull
    private boolean active;

    @NotNull
    private Set<AccessLevelType> accessLevels;
}
