package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;

import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.CompanyName;

import javax.validation.constraints.Positive;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class AddCruiseGroupDto {
    @CompanyName
    private String companyName;

    private String name;

    @Positive
    private long numberOfSeats;
    @Positive
    private Double price;

    private CruiseAddressDto cruiseAddress;

    private Set<CruisePictureDto> cruisePictures;


}
