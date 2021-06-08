package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;

import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.CompanyName;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddCruiseGroupDto {
    @CompanyName
    private String companyName;
    @NotEmpty
    private String name;

    @Positive
    @NotNull
    private long numberOfSeats;
    @Positive
    @NotNull
    private Double price;
    @Valid
    private CruiseAddressDto cruiseAddress;

    private List<CruisePictureDto> cruisePictures;
    @NotEmpty
    private String description;


}
