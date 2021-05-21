package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseAddress;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruisePicture;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CruiseAddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.addCruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.changeCruiseGroup.changeCruiseGroupDto;

import java.util.ArrayList;
import java.util.List;
/**
 * Klasa która zajmuje się mapowaniem obiektów klas dto na obiekty klas modelu
 */
public class CruiseGroupMapper {
    private CruiseGroupMapper(){}

    /**
     * Mapuje obiekt klasy dto na obiekt klasy CruiseAddress
     * @param addCruiseGroup obiekt klasy dto
     * @return obiekt klasy modelu który prezentuje adres wycieczki
     */
    public static CruiseAddress extractAddressForAddingCruiseGroup(addCruiseGroupDto addCruiseGroup){
        return new CruiseAddress(addCruiseGroup.getCruiseAddress().getStreet(),addCruiseGroup.getCruiseAddress().getStreetNumber(),
                addCruiseGroup.getCruiseAddress().getHarborName(),addCruiseGroup.getCruiseAddress().getCityName(),
                addCruiseGroup.getCruiseAddress().getCountryName());
    }

    /**
     * Mapuje obiekt klasy dto na listę obiektów klasy CruisePicture
     * @param addCruiseGroup obiekt klasy dto
     * @return lista obiektów klasy CruisePicture
     */
    public static List<CruisePicture> extractCruiseGroupPicturesFromAddingCruiseGroup(addCruiseGroupDto addCruiseGroup){
        List<CruisePicture> pictures= new ArrayList<>();//todo implement this
        return pictures;
    }

    /**
     * Mapuje obiekt klasy CruiseGroup na dto changeCruiseGroupDto
     * @param cruiseGroup obiekt podaway konwersji
     * @return obiekt dto
     */
    public static changeCruiseGroupDto toChangeCruiseGroupDto(CruiseGroup cruiseGroup){
        return new changeCruiseGroupDto(cruiseGroup.getName(),cruiseGroup.getNumberOfSeats(),cruiseGroup.getPrice(),toCruiseAddressDto(cruiseGroup.getAddress()),cruiseGroup.getVersion());
    }

    /**
     * Mapuje obiekt klasy CruiseAddress na dto CruiseGroupAddress
     * @param cruiseAddress obiekt klasy CruiseAddress podawany konwersji
     * @return obiekt klasy dto
     */
    public static CruiseAddressDto toCruiseAddressDto(CruiseAddress cruiseAddress){
        return new CruiseAddressDto(cruiseAddress.getStreet(),cruiseAddress.getStreetNumber(),cruiseAddress.getHarborName(),
                cruiseAddress.getCityName(),cruiseAddress.getCountryName(),cruiseAddress.getVersion()
        );
    }
}
