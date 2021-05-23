package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CompanyFacadeMow;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import java.util.List;

/**
 * Klasa która zarządza logiką biznesową firm
 */
@Stateful
public class CompanyManager implements CompanyManagerLocal {
    @Inject
    private CompanyFacadeMow companyFacadeMow;

    @Override
    public List<Company> getAllCompanies() throws BaseAppException {
        return companyFacadeMow.findAll();
    }

    @RolesAllowed("getBusinessWorkersForCompany")
    @Override
    public List<BusinessWorker> getBusinessWorkersForCompany(String companyName) throws BaseAppException {
        throw new UnsupportedOperationException();
    }
}
