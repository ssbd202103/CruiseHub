package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Rating;

import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
public class CreateRatingDto {
    @Login
    private String login;

    @Positive
    private Long cruiseId;

    @Rating
    private Double rating;
}
