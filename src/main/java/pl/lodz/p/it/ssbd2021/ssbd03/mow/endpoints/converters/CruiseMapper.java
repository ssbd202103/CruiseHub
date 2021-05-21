package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.NewCruiseDto;

public class CruiseMapper {
    private CruiseMapper() {
    }

    public static Cruise mapNewCruiseDtoToCruise(NewCruiseDto newCruiseDto) {
        return new Cruise(newCruiseDto.getStartDate(), newCruiseDto.getEndDate(), newCruiseDto.isActive(), newCruiseDto.getDescription(),
                newCruiseDto.getAvailable(), newCruiseDto.getCruisesGroup());
    }
}
