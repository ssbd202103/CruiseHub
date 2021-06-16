package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Administrator;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Moderator;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.CompanyMangerException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.AccountFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CompanyFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.Optional;

import static javax.ejb.TransactionAttributeType.MANDATORY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.ACCESS_LEVEL_DOES_NOT_EXIST_ERROR;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.OPERATION_NOT_AUTHORIZED_ERROR;

/**
 * Klasa która zarządza logiką biznesową firm
 */
@Stateful
@TransactionAttribute(MANDATORY)
@Interceptors(TrackingInterceptor.class)
public class CompanyManager implements CompanyManagerLocal {
    @Inject
    private CompanyFacadeMow companyFacadeMow;

    @Inject
    private AccountFacadeMow accountFacadeMow;

    @Context
    private SecurityContext context;

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
            Optional<AccessLevel> optionalAccessLevel = currentUser.getAccessLevels().stream()
                    .filter(BusinessWorker.class::isInstance).findAny();

            if (optionalAccessLevel.isEmpty()) {
                throw new CompanyMangerException(ACCESS_LEVEL_DOES_NOT_EXIST_ERROR);
            }

            BusinessWorker businessWorker = (BusinessWorker) optionalAccessLevel.get();
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

    @RolesAllowed("authenticatedUser")
    private Account getCurrentUser() throws BaseAppException {
        return accountFacadeMow.findByLogin(context.getUserPrincipal().getName());
    }

    private void setCreatedMetadata(Account creator, BaseEntity... entities) throws BaseAppException {
        AlterTypeWrapper insert = accountFacadeMow.getAlterTypeWrapperByAlterType(AlterType.INSERT);
        for (BaseEntity e : entities) {
            e.setAlterType(insert);
            e.setAlteredBy(creator);
            e.setCreatedBy(creator);
        }
    }
}
