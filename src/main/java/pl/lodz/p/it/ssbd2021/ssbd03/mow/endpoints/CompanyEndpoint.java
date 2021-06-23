package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.common.dto.MetadataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints.BaseEndpoint;
import pl.lodz.p.it.ssbd2021.ssbd03.common.mappers.MetadataMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.BusinessWorkerDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.converters.AccountMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.AddCompanyDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.CompanyDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters.CompanyMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.managers.CompanyManagerLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;

/**
 * Klasa która zajmuje się gromadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z firmami oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Stateful
@TransactionAttribute(REQUIRES_NEW)
@Interceptors(TrackingInterceptor.class)
public class CompanyEndpoint extends BaseEndpoint implements CompanyEndpointLocal {
    @Inject
    private CompanyManagerLocal companyManager;

    @Override
    public List<CompanyLightDto> getCompaniesInfo() throws BaseAppException {
        return companyManager.getAllCompanies().stream().map(CompanyMapper::mapCompanyToCompanyLightDto).collect(Collectors.toList());
    }

    @RolesAllowed("getBusinessWorkersForCompany")
    @Override
    public List<BusinessWorkerDto> getBusinessWorkersForCompany(String companyName) throws BaseAppException {
        return companyManager.getBusinessWorkersForCompany(companyName).stream()
                .map(AccountMapper::toBusinessWorkerDto).collect(Collectors.toList());
    }

    @RolesAllowed("getAllCompanies")
    @Override
    public List<CompanyDto> getAllCompanies() throws BaseAppException {
        return companyManager.getAllCompanies().stream().map(CompanyMapper::mapCompanyToCompanyDto).collect(Collectors.toList());
    }

    @RolesAllowed("addCompany")
    @Override
    public void addCompany(AddCompanyDto addCompanyDto) throws BaseAppException {
        Company company = CompanyMapper.mapAddCompanyDtoToCompany(addCompanyDto);
        companyManager.addCompany(company);
    }

    @RolesAllowed("authenticatedUser")
    @Override
    public MetadataDto getCompanyMetadata(long nip) throws BaseAppException {
        return MetadataMapper.toMetadataDto(companyManager.findByNIP(nip));
    }
}
