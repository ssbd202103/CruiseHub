package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CompanyLightDto;

public class CompanyMapper {
    private CompanyMapper() {
    }

    public static CompanyLightDto mapCompanyToCompanyLightDto(Company company) {
        return new CompanyLightDto(company.getName(), company.getNIP());
    }
}
