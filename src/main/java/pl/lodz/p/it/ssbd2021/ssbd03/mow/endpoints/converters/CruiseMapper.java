package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.NewCruiseDto;


/**
 * Klasa która zajmuje się mapowaniem obiektów klas dto na obiekty klas modelu
 */
public class CruiseMapper {
    private CruiseMapper() {
    }


    /**
     * Zajmuje się mapowaniem obiektu klasy wycieczki dto na obiekt wycieczki
     *
     * @param newCruiseDto obiekt dto reprezentujący wycieczke
     * @return zmapowany obiekt
     */
    public static Cruise newCruiseDtoToCruise(NewCruiseDto newCruiseDto) {
        return new Cruise(newCruiseDto.getStartDate(), newCruiseDto.getEndDate(), newCruiseDto.isActive(),
                newCruiseDto.getAvailable(), null);
    }

    public static CruiseDto cruiseToCruiseDto(Cruise cruise) {

        CruiseGroupDto cruiseGroupDto = CruiseGroupMapper.toCruiseGroupDto(cruise.getCruisesGroup());

        return new CruiseDto(cruise.getUuid(), cruise.getVersion(),
                cruise.getStartDate(), cruise.getEndDate(),
                cruise.isActive(), cruise.isAvailable(), cruiseGroupDto);
    }
}
