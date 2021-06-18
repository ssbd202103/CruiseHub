package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.ratings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.validation.constraints.NotEmpty;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveClientRatingDto {
    @Login
    private String login;

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String cruiseGroupName;
}
