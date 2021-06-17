package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.AttractionManagerException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.managers.BaseManagerMok;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Attraction;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.AttractionFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.ReservationFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.NON_REMOVABLE_ATTRACTION_CRUISE_ALREADY_PUBLISH;

/**
 * Klasa która zarządza logiką biznesową atrakcji
 */
@Stateful
@Interceptors(TrackingInterceptor.class)
public class AttractionManager extends BaseManagerMow implements AttractionManagerLocal {

    @Inject
    private AttractionFacadeMow attractionFacadeMow;


    @RolesAllowed("deleteAttraction")
    @Override
    public void deleteAttraction(long uuid) throws BaseAppException {
        Attraction attraction = attractionFacadeMow.findByUUID(uuid);
        Cruise cruise = attraction.getCruise();
        if(cruise.isPublished()){
            throw new AttractionManagerException(NON_REMOVABLE_ATTRACTION_CRUISE_ALREADY_PUBLISH);
        }
            attractionFacadeMow.remove(attraction);
    }

    @RolesAllowed("addAttraction")
    @Override
    public void addAttraction(Attraction attraction) throws BaseAppException {
        setCreatedMetadata(getCurrentUser(), attraction);
        attractionFacadeMow.create(attraction);
    }

    @RolesAllowed("editAttraction")
    @Override
    public void editAttraction(Attraction attraction) throws BaseAppException {
        throw new UnsupportedOperationException();
    }

    // TODO
    @PermitAll
    @Override
    public List<Attraction> findByCruiseUUID(UUID uuid) throws BaseAppException {
        return attractionFacadeMow.findByCruiseUUID(uuid);
    }
}