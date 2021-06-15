package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters;


import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups.CruiseGroupWithUUIDDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.RelatedCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.CruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.NewCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.MapperException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.NewCruiseDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.IllegalFormatException;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CRUISE_MAPPER_DATE_PARSE;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CRUISE_MAPPER_UUID_PARSE;


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
    public static Cruise mapNewCruiseDtoToCruise(NewCruiseDto newCruiseDto) throws MapperException {
        try {
            return new Cruise(LocalDateTime.ofInstant(Instant.parse(newCruiseDto.getStartDate()), ZoneId.systemDefault()),
                    LocalDateTime.ofInstant(Instant.parse(newCruiseDto.getEndDate()), ZoneId.systemDefault()),null);
        } catch (DateTimeParseException e) {
            throw new MapperException(CRUISE_MAPPER_DATE_PARSE);
        }
    }

    public static CruiseDto mapCruiseToCruiseDto(Cruise cruise) {

        CruiseGroupWithUUIDDto cruiseGroupDto = CruiseGroupMapper.toCruiseGroupWithUUIDDto(cruise.getCruisesGroup());

        return new CruiseDto(cruise.getUuid(), cruise.getVersion(),
                cruise.getStartDate(), cruise.getEndDate(),
                cruise.isActive(), cruiseGroupDto);
    }

    public static RelatedCruiseDto toRelatedCruiseDto(Cruise cruise) {
        return new RelatedCruiseDto(cruise.getUuid(), cruise.getStartDate(), cruise.getEndDate(), cruise.isActive());
    }
}
