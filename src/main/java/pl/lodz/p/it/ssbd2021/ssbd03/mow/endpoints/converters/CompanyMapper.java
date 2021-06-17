package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.AddCompanyDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.CompanyDto;

public class CompanyMapper {
    private CompanyMapper() {
    }

    public static CompanyLightDto mapCompanyToCompanyLightDto(Company company) {
        return new CompanyLightDto(company.getName(), company.getNIP());
    }

    public static CompanyDto mapCompanyToCompanyDto(Company company) {
        return new CompanyDto(company.getName(), company.getPhoneNumber(), company.getNIP(), company.getAddress().getHouseNumber(),
                company.getAddress().getStreet(), company.getAddress().getPostalCode(), company.getAddress().getCity(), company.getAddress().getCountry());
    }


    /**
     * Mapuje obiekt klasy AddCompanyDto na obietk klasy Company
     *
     * @param company obiekt klasy AddCompanyDto
     * @return obiekt klasy Company
     */
    public static Company mapAddCompanyDtoToCompany(AddCompanyDto company) {
        return new Company(
                mapAddressDtoToAddress(company.getAddressDto()),
                company.getName(),
                company.getPhoneNumber(),
                company.getNip());
    }

    /**
     * Mapuje obiekt klasy AddressDto na obiekt klasy Address
     *
     * @param address obiekt klasy AddressDto
     * @return obiekt klasy Address
     */
    public static Address mapAddressDtoToAddress(AddressDto address) {
        return new Address(
                address.getHouseNumber(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity(),
                address.getCountry());
    }
}
