package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Attraction;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.AttractionManagerException;
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

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.ATTRACTION_EDIT_CRUISE_PUBLISHED_ERROR;
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
    public void deleteAttraction(long id) throws BaseAppException {
        Attraction attraction = attractionFacadeMow.findById(id);
        Cruise cruise = attraction.getCruise();
        if (cruise.isPublished()) {
            throw new AttractionManagerException(NON_REMOVABLE_ATTRACTION_CRUISE_ALREADY_PUBLISH);
        }
        attractionFacadeMow.remove(attraction);
    }

    @RolesAllowed("addAttraction")
    @Override
    public UUID addAttraction(Attraction attraction) throws BaseAppException {
        setCreatedMetadata(getCurrentUser(), attraction);
        attractionFacadeMow.create(attraction);
        return attraction.getUuid();
    }

    @RolesAllowed("editAttraction")
    @Override
    public void editAttraction(UUID attractionUUID, String newName, String newDescription, double newPrice, int newNumberOfSeats, long version) throws BaseAppException {
        Attraction attraction = attractionFacadeMow.findByUUID(attractionUUID);
        if (attraction.getCruise().isPublished()) {
            throw new AttractionManagerException(ATTRACTION_EDIT_CRUISE_PUBLISHED_ERROR);
        }

        attraction.setName(newName);
        attraction.setDescription(newDescription);
        attraction.setPrice(newPrice);
        attraction.setNumberOfSeats(newNumberOfSeats);
        setUpdatedMetadata(attraction);

        attraction.setVersion(version);
        attractionFacadeMow.edit(attraction);
    }

    // TODO
    @PermitAll
    @Override
    public List<Attraction> findByCruiseUUID(UUID uuid) throws BaseAppException {
        return attractionFacadeMow.findByCruiseUUID(uuid);
    }
}