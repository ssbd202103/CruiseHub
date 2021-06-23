package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters;


import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups.CruiseGroupWithUUIDDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.*;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.MapperException;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CRUISE_MAPPER_DATE_PARSE;


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
        return new CruiseDto(cruise.getUuid().toString(), cruise.getVersion(),
                cruise.getStartDate(), cruise.getEndDate(),
                cruise.isActive(), cruiseGroupDto, cruiseGroupDto.getNumberOfSeats() - cruise.getReservations().stream().mapToLong(Reservation::getNumberOfSeats).sum());


    }

    public static RelatedCruiseDto toRelatedCruiseDto(Cruise cruise) {
        return new RelatedCruiseDto(cruise.getUuid(), cruise.getStartDate(), cruise.getEndDate(), cruise.isActive());
    }

    /**
     * Metoda mapujaca liste wycieczek na liste CruiseGroupWithCruisesDto
     * @param cruises lista wycieczek
     * @return Lista wycieczek w postaci CruiseGroupWithCruisesDto
     */
    public static List<CruiseGroupWithCruisesDto> toListOfCruiseGroupsWithCruisesDto(List<Cruise> cruises) {
        List<CruiseGroup> cruiseGroups = new ArrayList<>();
        List<CruiseGroupWithCruisesDto> result = new ArrayList<>();

        for (int i = 0; i < cruises.size(); i++) {
            CruiseGroup cg = cruises.get(i).getCruisesGroup();
            if (!cruiseGroups.contains(cg)) {
                cruiseGroups.add(cg);
                String img = null;
                if (cg.getCruisePictures().size() > 0) {

                    img = cg.getCruisePictures().get(0).getImg();
                }
                CruiseGroupWithCruisesDto c = new CruiseGroupWithCruisesDto(cg.getUuid(), cg.getName(), cg.getPrice(), img, new ArrayList<>());
                for (int j = i; j < cruises.size(); j++) {
                    if (cruises.get(j).getCruisesGroup().getUuid() == c.getUuid()) {
                        c.getRelatedCruises().add(toShortCruiseDto(cruises.get(j)));
                    }
                }
                result.add(c);
            }
        }

        return result;
    }

    private static ShortCruiseDto toShortCruiseDto(Cruise cruise) {
        return new ShortCruiseDto(cruise.getUuid(), cruise.getStartDate(), cruise.getEndDate());
    }
}
