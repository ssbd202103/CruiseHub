package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Attraction;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.AccountManagerException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.AttractionManagerException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.converters.AccountMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.AttractionFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

/**
 * Klasa która zarządza logiką biznesową atrakcji
 */
@Stateful
@Interceptors(TrackingInterceptor.class)
public class AttractionManager extends BaseManagerMow implements AttractionManagerLocal {

    @Inject
    private AttractionFacadeMow attractionFacadeMow;

    @Inject
    private CruiseFacadeMow cruiseFacadeMow;

    @RolesAllowed("deleteAttraction")
    @Override
    public void deleteAttraction(UUID uuid) throws BaseAppException {
        Attraction attraction = attractionFacadeMow.findByUUID(uuid);

        validateAttractionChangesPermission(attraction.getCruise(),
                ATTRACTION_DELETE_CRUISE_PUBLISHED_ERROR,
                ATTRACTION_DELETE_CRUISE_DISABLED_ERROR,
                ATTRACTION_DELETE_CRUISE_ALREADY_STARTED_ERROR
        );

        attractionFacadeMow.remove(attraction);
    }

    @RolesAllowed("addAttraction")
    @Override
    public UUID addAttraction(Attraction attraction, UUID cruiseUUID) throws BaseAppException {
        Cruise cruise = cruiseFacadeMow.findByUUID(cruiseUUID);

        validateAttractionChangesPermission(cruise,
                ATTRACTION_CREATION_CRUISE_PUBLISHED_ERROR,
                ATTRACTION_CREATION_CRUISE_DISABLED_ERROR,
                ATTRACTION_CREATION_CRUISE_ALREADY_STARTED_ERROR
        );

        attraction.setCruise(cruise);
        setCreatedMetadata(getCurrentUser(), attraction);
        attractionFacadeMow.create(attraction);
        return attraction.getUuid();
    }

    @RolesAllowed("editAttraction")
    @Override
    public void editAttraction(UUID attractionUUID, String newName, String newDescription, double newPrice, int newNumberOfSeats, long version) throws BaseAppException {
        Attraction attraction = attractionFacadeMow.findByUUID(attractionUUID);

        validateAttractionChangesPermission(attraction.getCruise(),
                ATTRACTION_EDIT_CRUISE_PUBLISHED_ERROR,
                ATTRACTION_EDIT_CRUISE_DISABLED_ERROR,
                ATTRACTION_EDIT_CRUISE_ALREADY_STARTED_ERROR
        );

        attraction.setName(newName);
        attraction.setDescription(newDescription);
        attraction.setPrice(newPrice);
        attraction.setNumberOfSeats(newNumberOfSeats);
        setUpdatedMetadata(attraction);

        attraction.setVersion(version);
        attractionFacadeMow.edit(attraction);
    }


    @PermitAll //TODO
    @Override
    public List<Attraction> findByCruiseUUID(UUID uuid) throws BaseAppException {
        return attractionFacadeMow.findByCruiseUUID(uuid);
    }

    private void validateAttractionChangesPermission(Cruise cruise, String cruisePublishedError, String cruiseDisabledError, String cruiseStartedError) throws BaseAppException {
        BusinessWorker businessWorker = (BusinessWorker) AccountMapper.getAccessLevel(getCurrentUser(), AccessLevelType.BUSINESS_WORKER);

        if (!cruise.getCruisesGroup().getCompany().equals(businessWorker.getCompany())) {
            throw new AttractionManagerException(NOT_YOURS_CRUISE);
        }

        if (cruise.isPublished()) {
            throw new AccountManagerException(cruisePublishedError);
        }

        if (!cruise.isActive()) {
            throw new AccountManagerException(cruiseDisabledError);
        }

        if (cruise.getStartDate().isBefore(LocalDateTime.now())) {
            throw new AttractionManagerException(cruiseStartedError);
        }
    }
}