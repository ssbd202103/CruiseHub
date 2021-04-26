package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.CompanyMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.CompanyManagerLocal;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateful
public class CompanyEndpoint implements CompanyEndpointLocal {
    @EJB
    private CompanyManagerLocal companyManager;

    @Override
    public List<CompanyLightDto> getCompaniesInfo() {
        return companyManager.getAllCompanies().stream().map(CompanyMapper::mapCompanyToCompanyLightDto).collect(Collectors.toList());
    }
}
