package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters;

import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Rating;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.ratings.RatingDto;

@NoArgsConstructor
public class RatingMapper {
    public static RatingDto mapRatingToRatingDto(Rating rating) {
        return new RatingDto(rating.getAccount().getLogin(), rating.getCruiseGroup().getName(), rating.getRating(), rating.getCruiseGroup().getUuid());
    }
}
