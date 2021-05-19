package pl.lodz.p.it.ssbd2021.ssbd03.mok.facades;

import org.hibernate.exception.ConstraintViolationException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;


public abstract class AbstractFacade<T> {

    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) throws FacadeException {
        try {
            getEntityManager().persist(entity);
            getEntityManager().flush();
        } catch (PersistenceException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw (ConstraintViolationException) e.getCause();
            }
            throw FacadeException.databaseOperation();
        }
    }


    public void edit(T entity) throws FacadeException {
        try {
            getEntityManager().merge(entity);
            getEntityManager().flush();
        } catch (PersistenceException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw (ConstraintViolationException) e.getCause();
            }
            throw FacadeException.databaseOperation();
        }
    }

    public void remove(T entity) throws FacadeException {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
        } catch (PersistenceException exp) {
            throw FacadeException.databaseOperation();
        }
    }

    public T find(Object id) throws FacadeException {
        try {
            return getEntityManager().find(entityClass, id);
        } catch (PersistenceException exp) {
            throw FacadeException.databaseOperation();
        }
    }

    public List findAll() throws FacadeException {
        try {
            CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            return getEntityManager().createQuery(cq).getResultList();
        } catch (PersistenceException exp) {
            throw FacadeException.databaseOperation();
        }
    }

    public List findRange(int[] range) throws FacadeException {
        try {
            CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            javax.persistence.Query q = getEntityManager().createQuery(cq);
            q.setMaxResults(range[1] - range[0] + 1);
            q.setFirstResult(range[0]);
            return q.getResultList();
        } catch (PersistenceException exp) {
            throw FacadeException.databaseOperation();
        }
    }

    public Long count() throws FacadeException {
        try {
            CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder().createQuery();
            javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
            cq.select(getEntityManager().getCriteriaBuilder().count(rt));
            javax.persistence.Query q = getEntityManager().createQuery(cq);
            return ((Long) q.getSingleResult());
        } catch (PersistenceException exp) {
            throw FacadeException.databaseOperation();
        }

    }

}
