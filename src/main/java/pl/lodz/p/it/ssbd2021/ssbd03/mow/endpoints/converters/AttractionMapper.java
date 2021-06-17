package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Attraction;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.AddAttractionDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions.AttractionDto;

public class AttractionMapper {

    private AttractionMapper() {
    }

    public static AttractionDto toAttractionDto(Attraction attraction) {
        return new AttractionDto(attraction.getName(), attraction.getDescription(), attraction.getPrice(), attraction.getNumberOfSeats());
    }

    public static Attraction toAttraction(AddAttractionDto attractionDto, Cruise cruise) {
        return new Attraction(attractionDto.getName(), attractionDto.getDescription(), attractionDto.getPrice(), attractionDto.getNumberOfSeats(), cruise);
    }
}
