package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.changeCruiseGroup;

import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CruiseAddressDto;

import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class changeCruiseGroupDto {

    private String name;

    @Positive
    private long numberOfSeats;

    @Positive
    private Double price;

    private CruiseAddressDto cruiseAddress;
    @Positive
    private long version;
}
