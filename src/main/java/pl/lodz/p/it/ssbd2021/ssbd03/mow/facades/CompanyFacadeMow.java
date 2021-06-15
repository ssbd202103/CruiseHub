package pl.lodz.p.it.ssbd2021.ssbd03.mow.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.common.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

import static javax.ejb.TransactionAttributeType.MANDATORY;

@Stateless
@TransactionAttribute(MANDATORY)
@Interceptors(TrackingInterceptor.class)
public class CompanyFacadeMow extends AbstractFacade<Company> {

    @PersistenceContext(unitName = "ssbd03mowPU")
    private EntityManager em;

    public CompanyFacadeMow() {
        super(Company.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    @PermitAll
    public List<Company> findAll() throws FacadeException {
        return super.findAll();
    }

    @Override
    @RolesAllowed("editCompany")
    public void edit(Company entity) throws FacadeException {
        super.edit(entity);
    }

    @Override
    @RolesAllowed("addCompany")
    public void create(Company entity) throws FacadeException {
        super.create(entity);
    }

    @PermitAll
    public Company findByName(String companyName) throws BaseAppException {
        TypedQuery<Company> tq = em.createNamedQuery("Company.findByName", Company.class);
        tq.setParameter("name", companyName);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    @RolesAllowed("getBusinessWorkersForCompany")
    public List<BusinessWorker> getBusinessWorkersByCompanyName(String companyName) throws BaseAppException {
        TypedQuery<BusinessWorker> tq = em.createNamedQuery("Company.findBusinessWorkersByCompanyName", BusinessWorker.class);
        tq.setParameter("companyName", companyName);
        try {
            return tq.getResultList();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }
}
