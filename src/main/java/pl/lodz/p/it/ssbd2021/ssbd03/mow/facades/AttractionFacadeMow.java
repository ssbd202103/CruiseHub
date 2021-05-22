package pl.lodz.p.it.ssbd2021.ssbd03.mow.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Attraction;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class AttractionFacadeMow extends AbstractFacade<Attraction> {

    @PersistenceContext(unitName = "ssbd03mowPU")
    private EntityManager em;

    public AttractionFacadeMow() { super(Attraction.class); }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Attraction> findAll() { //TODO throws FacadeException {
        return super.findAll();
    }

    @Override
    public void edit(Attraction entity) { //TODo throws FacadeException {
        super.edit(entity);
    }

    @Override
    public void create(Attraction entity) { //TODO throws FacadeException {
        super.create(entity);
    }

    public Attraction findByName(String name) throws BaseAppException {
        TypedQuery<Attraction> tq = em.createNamedQuery("Attraction.findByName", Attraction.class);
        tq.setParameter("name", name);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    public Attraction deleteAttraction(String name) throws BaseAppException {
        TypedQuery<Attraction> tq = em.createNamedQuery("Attraction.deleteByName", Attraction.class);
        tq.setParameter("name", name);
        try {
            return tq.getSingleResult();
        }catch (NoResultException e){
            throw FacadeException.noSuchElement();
        }
    }
}