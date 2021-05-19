package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CompanyFacadeMow;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Klasa która zarządza logiką biznesową firm
 */
@Stateful
public class CompanyManager implements CompanyManagerLocal {
    @EJB
    private CompanyFacadeMow companyFacadeMow;

    @Override
    public List<Company> getAllCompanies() throws BaseAppException {
        return companyFacadeMow.findAll();
    }
}
