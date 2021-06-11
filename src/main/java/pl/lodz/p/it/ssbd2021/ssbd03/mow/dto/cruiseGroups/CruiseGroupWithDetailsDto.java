package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups;

import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.CruiseAddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.CruiseForCruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.CruisePictureDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.CompanyName;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;

import javax.validation.constraints.Positive;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class CruiseGroupWithDetailsDto {

        @CompanyName
        private CompanyLightDto company;
        @Name
        private String name;

        @Positive
        private long numberOfSeats;
        @Positive
        private Double price;

        private CruiseAddressDto cruiseAddress;

        private List<CruisePictureDto> cruisePictures;
        @Positive
        private long version;

        private String description;

        private boolean active;

        private List<CruiseForCruiseGroupDto> cruises;
        private String start_time;
        private String end_time;
    }

