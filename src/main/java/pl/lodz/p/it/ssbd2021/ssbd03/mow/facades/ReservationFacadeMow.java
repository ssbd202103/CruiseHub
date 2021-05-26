package pl.lodz.p.it.ssbd2021.ssbd03.mow.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(TrackingInterceptor.class)
public class ReservationFacadeMow extends AbstractFacade<Reservation> {

    @PersistenceContext(unitName = "ssbd03mowPU")
    private EntityManager em;

    public ReservationFacadeMow() {
        super(Reservation.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Reservation> findAll() throws FacadeException {
        return super.findAll();
    }

    @Override
    public void edit(Reservation entity) throws FacadeException {
        super.edit(entity);
    }

    @Override
    public void create(Reservation entity) throws FacadeException {
        super.create(entity);
    }

    public Reservation findByUUID(UUID uuid) throws BaseAppException {
        TypedQuery<Reservation> tq = em.createNamedQuery("Reservation.findByUUID", Reservation.class);
        tq.setParameter("uuid", uuid);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    public List<Reservation> findCruiseReservations(long id) throws BaseAppException {
        TypedQuery<Reservation> tq = em.createNamedQuery("Reservation.findCruiseReservations", Reservation.class);
        tq.setParameter("id", id);
        try {
            return tq.getResultList();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    public List<Reservation> findWorkerCruiseReservations(long id) throws BaseAppException {
        //TODO Metoda znajdujaca id uzytkownika który jest aktualnie uzytkujacy jako bisnez worker / zmienić query
        //
        //
        TypedQuery<Reservation> tq = em.createNamedQuery("Reservation.findCruiseReservations", Reservation.class);
        tq.setParameter("id", id);
        try {
            return tq.getResultList();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    @RolesAllowed("removeClientReservation")
    public Reservation findReservationByUuidAndLogin(UUID uuid, String login) throws BaseAppException {
        TypedQuery<Reservation> tq = em.createNamedQuery("Reservation.findByUUIDAndLogin", Reservation.class);
        tq.setParameter("uuid", uuid.toString());
        tq.setParameter("login", login);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    @RolesAllowed("viewSelfReservations")
    public List<Reservation> findReservationByLogin(String login) throws BaseAppException {
        TypedQuery<Reservation> tq = em.createNamedQuery("Reservation.findByLogin", Reservation.class);
        tq.setParameter("login", login);
        try {
            return tq.getResultList();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }
}