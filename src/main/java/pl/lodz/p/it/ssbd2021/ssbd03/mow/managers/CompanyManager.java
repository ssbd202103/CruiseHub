package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CompanyFacadeMow;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;

import static javax.ejb.TransactionAttributeType.MANDATORY;

/**
 * Klasa która zarządza logiką biznesową firm
 */
@Stateful
@TransactionAttribute(MANDATORY)
public class CompanyManager implements CompanyManagerLocal {
    @Inject
    private CompanyFacadeMow companyFacadeMow;

    @PermitAll
    @Override
    public List<Company> getAllCompanies() throws BaseAppException {
        return companyFacadeMow.findAll();
    }
}
