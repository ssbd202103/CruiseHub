package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.*;


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
    public static Cruise mapNewCruiseDtoToCruise(NewCruiseDto newCruiseDto) {
        return new Cruise(newCruiseDto.getStartDate(), newCruiseDto.getEndDate(), newCruiseDto.isActive(),
                newCruiseDto.getAvailable(), null);
    }

    public static CruiseDto mapCruiseToCruiseDto(Cruise cruise) {

        CruiseGroupWithUUIDDto cruiseGroupDto = CruiseGroupMapper.toCruiseGroupWithUUIDDto(cruise.getCruisesGroup());

        return new CruiseDto(cruise.getUuid().toString(), cruise.getVersion(),
                cruise.getStartDate(), cruise.getEndDate(),
                cruise.isActive(), cruise.isAvailable(), cruiseGroupDto);
    }

    public static RelatedCruiseDto toRelatedCruiseDto(Cruise cruise) {
        return new RelatedCruiseDto(cruise.getUuid(), cruise.getStartDate(), cruise.getEndDate(), cruise.isActive(), cruise.isAvailable());
    }
}
