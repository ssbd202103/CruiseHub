package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CompanyLightDto;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CompanyEndpointLocal {
    List<CompanyLightDto> getCompaniesInfo();
}
