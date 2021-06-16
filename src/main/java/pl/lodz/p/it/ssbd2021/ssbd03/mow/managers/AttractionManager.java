package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.managers.BaseManagerMok;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Attraction;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.AttractionFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.UUID;

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
    public void deleteAttraction(String name) throws BaseAppException {
        Attraction attraction = this.attractionFacadeMow.findByName(name);
        //TODO sprawdzenie czy atrakcja nie jest zarezerwowana
        if (attraction != null) {   //if na te chwile do zmiany

        } else {
            this.attractionFacadeMow.deleteAttraction(name);
        }
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