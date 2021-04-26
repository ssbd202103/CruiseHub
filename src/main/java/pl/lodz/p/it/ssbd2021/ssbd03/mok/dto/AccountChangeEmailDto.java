package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@NoArgsConstructor
public class AccountChangeEmailDto extends AbstractAccountDTO {
    @Email
    @NotNull
    private String newEmail;

    public AccountChangeEmailDto(String login, Long version, String newEmail) {
        super(login, version);

        this.newEmail = newEmail;
    }
}
