package pl.lodz.p.it.ssbd2021.ssbd03.mow.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AbstractFacade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
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
}
