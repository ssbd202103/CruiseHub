package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Data
@Getter
@Setter
public class CruiseGroupWithUUIDDto extends CruiseGroupDto {
    private UUID uuid;
    private String description;
    private Double avgRating;
    private List<RatingDto> ratings;

    public CruiseGroupWithUUIDDto(UUID uuid,
                                  String description,
                                  Double avgRating,
                                  List<RatingDto> ratings,
                                  CompanyLightDto company,
                                  String name,
                                  long numberOfSeats,
                                  Double price,
                                  CruiseAddressDto cruiseAddress,
                                  List<CruisePictureDto>cruisePictures,
                                  long version,
                                  boolean active) {
        super(company, name, numberOfSeats, price, cruiseAddress, cruisePictures, version, active);

        this.uuid = uuid;
        this.description = description;
        this.avgRating = avgRating;
        this.ratings = ratings;
    }
}
