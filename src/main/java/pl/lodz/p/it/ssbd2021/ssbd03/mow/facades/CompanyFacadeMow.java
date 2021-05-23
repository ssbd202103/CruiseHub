package pl.lodz.p.it.ssbd2021.ssbd03.mow.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AbstractFacade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
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
    public List<Company> findAll() throws FacadeException {
        return super.findAll();
    }

    @Override
    public void edit(Company entity) throws FacadeException {
        super.edit(entity);
    }

    @Override
    public void create(Company entity) throws FacadeException {
        super.create(entity);
    }

    public Company getCompanyByName(String companyName) throws BaseAppException {
        TypedQuery<Company> tq = em.createNamedQuery("Company.findByName", Company.class);
        tq.setParameter("name", companyName);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }


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
