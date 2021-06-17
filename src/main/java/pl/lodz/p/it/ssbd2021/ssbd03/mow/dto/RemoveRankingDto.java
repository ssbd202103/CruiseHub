package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.validation.constraints.NotEmpty;

import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveRankingDto {
    @Login
    private String login;

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private UUID cruiseGroupUUID;
}
