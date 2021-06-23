package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseAddress;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruisePicture;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups.CruiseGroupWithDetailsDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.CruiseAddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.CruiseForCruiseGroupDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.CruisePictureDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.ratings.RatingDto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Klasa która zajmuje się mapowaniem obiektów klas dto na obiekty klas modelu
 */
public class CruiseGroupMapper {
    private CruiseGroupMapper() {
    }

    /**
     * Mapuje obiekt klasy dto na obiekt klasy CruiseAddress
     *
     * @param addCruiseGroup obiekt klasy dto
     * @return obiekt klasy modelu który prezentuje adres wycieczki
     */
    public static CruiseAddress extractAddressForAddingCruiseGroup(AddCruiseGroupDto addCruiseGroup) {
        return new CruiseAddress(addCruiseGroup.getCruiseAddress().getStreet(), addCruiseGroup.getCruiseAddress().getStreetNumber(),
                addCruiseGroup.getCruiseAddress().getHarborName(), addCruiseGroup.getCruiseAddress().getCityName(),
                addCruiseGroup.getCruiseAddress().getCountryName());
    }

    /**
     * Mapuje obiekt klasy dto na listę obiektów klasy CruisePicture
     *
     * @param addCruiseGroup obiekt klasy dto
     * @return lista obiektów klasy CruisePicture
     */
    public static List<CruisePicture> extractCruiseGroupPicturesFromAddingCruiseGroup(AddCruiseGroupDto addCruiseGroup) {
        List<CruisePicture> pictures = new ArrayList<>();
        if (addCruiseGroup.getCruisePictures().size() > 0) {

            for (CruisePictureDto dto : addCruiseGroup.getCruisePictures()
            ) {
                pictures.add(new CruisePicture(dto.getDataURL(), "zdjecie"));
            }
        }
        return pictures;
    }


    /**
     * Mapuje obiekt klasy CruiseAddress na dto CruiseGroupAddress
     *
     * @param cruiseAddress obiekt klasy CruiseAddress podawany konwersji
     * @return obiekt klasy dto
     */
    public static CruiseAddressDto toCruiseAddressDto(CruiseAddress cruiseAddress) {
        return new CruiseAddressDto(cruiseAddress.getStreet(), cruiseAddress.getStreetNumber(), cruiseAddress.getHarborName(),
                cruiseAddress.getCityName(), cruiseAddress.getCountryName()
        );
    }

    /**
     * Mapuje obiekt klasy CruisePictures na dto CruisePicturesDto
     *
     * @param cruisePicture obiekt klasy CruisePictures podawany konwersji
     * @return obiekt klasy dto
     */
    public static CruisePictureDto toCruisePictureDto(CruisePicture cruisePicture) {
        return new CruisePictureDto(cruisePicture.getImg(), cruisePicture.getImgName(), cruisePicture.getVersion());
    }

    public static List<CruiseForCruiseGroupDto> toCruiseForCruiseGroupDtos(List<Cruise> cruises) throws BaseAppException {
        List<CruiseForCruiseGroupDto> cruisesForCruiseGroup = new ArrayList<>();

        for (Cruise cruise : cruises) {
            cruisesForCruiseGroup.add(new CruiseForCruiseGroupDto(cruise.getUuid(), cruise.getStartDate(), cruise.getEndDate(), cruise.isActive(), cruise.isPublished(), cruise.getVersion()));
        }

        return cruisesForCruiseGroup;
    }

    /**
     * Mapuje obiekt klasy CruiseGroup na dto CruiseGroupDto
     *
     * @param cruiseGroup grupa wycieczek podawana konwersji
     * @return obiekt klasy dto
     */
    public static CruiseGroupDto toCruiseGroupDto(CruiseGroup cruiseGroup) {
        CompanyLightDto company = CompanyMapper.mapCompanyToCompanyLightDto(cruiseGroup.getCompany());
        CruiseAddressDto address = CruiseGroupMapper.toCruiseAddressDto(cruiseGroup.getAddress());

        return new CruiseGroupDto(company, cruiseGroup.getName(), cruiseGroup.getNumberOfSeats(), cruiseGroup.getPrice(), address,
                cruiseGroup.getCruisePictures().stream().map(CruiseGroupMapper::toCruisePictureDto).collect(Collectors.toList()),
                cruiseGroup.getVersion(), cruiseGroup.isActive());
    }

    public static CruiseGroupWithUUIDDto toCruiseGroupWithUUIDDto(CruiseGroup cruiseGroup) {
        CompanyLightDto company = CompanyMapper.mapCompanyToCompanyLightDto(cruiseGroup.getCompany());
        CruiseAddressDto address = CruiseGroupMapper.toCruiseAddressDto(cruiseGroup.getAddress());
        List<RatingDto> ratings = cruiseGroup.getRatings().stream().map(RatingMapper::toRatingDto).collect(Collectors.toList());

        return new CruiseGroupWithUUIDDto(cruiseGroup.getUuid(), cruiseGroup.getDescription(), cruiseGroup.getAverageRating(), ratings, company, cruiseGroup.getName(), cruiseGroup.getNumberOfSeats(), cruiseGroup.getPrice(), address,
                cruiseGroup.getCruisePictures().stream().map(CruiseGroupMapper::toCruisePictureDto).collect(Collectors.toList()),
                cruiseGroup.getVersion(), cruiseGroup.isActive());
    }

    public static CruiseGroupWithDetailsDto toCruiseGroupWithDetailsDto(CruiseGroup cruiseGroup, List<Cruise> cruies) throws BaseAppException {
        CompanyLightDto company = CompanyMapper.mapCompanyToCompanyLightDto(cruiseGroup.getCompany());
        CruiseAddressDto address = CruiseGroupMapper.toCruiseAddressDto(cruiseGroup.getAddress());
        List<CruiseForCruiseGroupDto> cruises = CruiseGroupMapper.toCruiseForCruiseGroupDtos(cruies);
        if (cruies.size() > 0) {
            return new CruiseGroupWithDetailsDto(cruiseGroup.getUuid(), company, cruiseGroup.getName(), cruiseGroup.getNumberOfSeats(), cruiseGroup.getPrice(), address,
                    cruiseGroup.getCruisePictures().stream().map(CruiseGroupMapper::toCruisePictureDto).collect(Collectors.toList()),
                    cruiseGroup.getVersion(), cruiseGroup.getDescription(), cruiseGroup.isActive(), cruises, cruies.get(0).getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE), cruies.get(0).getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        } else
            return new CruiseGroupWithDetailsDto(cruiseGroup.getUuid(), company, cruiseGroup.getName(), cruiseGroup.getNumberOfSeats(), cruiseGroup.getPrice(), address,
                    cruiseGroup.getCruisePictures().stream().map(CruiseGroupMapper::toCruisePictureDto).collect(Collectors.toList()),
                    cruiseGroup.getVersion(), cruiseGroup.getDescription(), cruiseGroup.isActive(), cruises, "", "");
    }
}
