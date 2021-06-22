package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Administrator;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Moderator;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Attraction;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.CompanyMangerException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.converters.AccountMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CompanyFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

import static javax.ejb.TransactionAttributeType.MANDATORY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.OPERATION_NOT_AUTHORIZED_ERROR;

/**
 * Klasa która zarządza logiką biznesową firm
 */
@Stateful
@TransactionAttribute(MANDATORY)
@Interceptors(TrackingInterceptor.class)
public class CompanyManager extends BaseManagerMow implements CompanyManagerLocal {

    @Inject
    private CompanyFacadeMow companyFacadeMow;

    @Override
    public List<Company> getAllCompanies() throws BaseAppException {
        return companyFacadeMow.findAll();
    }

    @RolesAllowed("getBusinessWorkersForCompany")
    @Override
    public List<BusinessWorker> getBusinessWorkersForCompany(String companyName) throws BaseAppException {
        Account currentUser = getCurrentUser();

        //checking if businessWorker is neither Admin nor Moderator and wants to check workers from his own company
        if (currentUser.getAccessLevels().stream()
                .noneMatch(accessLevel -> accessLevel instanceof Moderator || accessLevel instanceof Administrator)
        ) {

            BusinessWorker businessWorker = (BusinessWorker) AccountMapper.getAccessLevel(currentUser, AccessLevelType.BUSINESS_WORKER);

            if (!businessWorker.getCompany().getName().equals(companyName)) {
                throw new CompanyMangerException(OPERATION_NOT_AUTHORIZED_ERROR);
            }
        }

        return companyFacadeMow.getBusinessWorkersByCompanyName(companyName);
    }

    @RolesAllowed("addCompany")
    @Override
    public void addCompany(Company company) throws BaseAppException {
        Account moderator = getCurrentUser();
        setCreatedMetadata(moderator, company, company.getAddress());

        companyFacadeMow.create(company);
    }

    @PermitAll //TODO
    @Override
    public Company findByNIP(long nip) throws BaseAppException {
        return companyFacadeMow.findByNIP(nip);
    }


}
