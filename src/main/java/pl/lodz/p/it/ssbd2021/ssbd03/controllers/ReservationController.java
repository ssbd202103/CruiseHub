package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.ControllerException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RemoveClientReservationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.ReservationEndpointLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.security.ETagFilterBinding;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;


@Path("/reservation")
@RequestScoped
public class ReservationController {
    @Inject
    private ReservationEndpointLocal reservationEndpointLocal;

    @DELETE
    @ETagFilterBinding
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/{reservationVersion}/{clientLogin}/{reservationUuid}")
    public void removeReservation(@PathParam("reservationVersion") long reservationVersion,
                                  @PathParam("clientLogin") String clientLogin,
                                  @PathParam("reservationUuid") UUID reservationUuid,
                                  @HeaderParam("If-Match") @NotNull(message = CONSTRAINT_NOT_NULL) @NotEmpty(message = CONSTRAINT_NOT_EMPTY) @Valid String etag) throws BaseAppException {
        RemoveClientReservationDto reservationDto = new RemoveClientReservationDto(reservationVersion, reservationUuid.toString(), clientLogin);
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(etag, reservationDto)) {
            throw ControllerException.etagIdentityIntegrity();
        }
        reservationEndpointLocal.removeClientReservation(reservationDto);
    }


}
