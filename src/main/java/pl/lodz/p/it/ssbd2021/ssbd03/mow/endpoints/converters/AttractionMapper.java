package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Attraction;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.AttractionDto;

public class AttractionMapper {
    public static AttractionDto toAttractionDto(Attraction attraction) {
        return new AttractionDto(attraction.getName(), attraction.getDescription(), attraction.getPrice(), attraction.getNumberOfSeats());
    }
}
