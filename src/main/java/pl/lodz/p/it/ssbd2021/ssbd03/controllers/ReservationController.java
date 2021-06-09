package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RemoveClientReservationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.ReservationEndpointLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;


@Path("/reservation")
@RequestScoped
public class ReservationController {
    @Inject
    private ReservationEndpointLocal reservationEndpointLocal;

    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/{reservationVersion}/{clientLogin}/{reservationUuid}")
    public void removeReservation(@PathParam("reservationVersion") long reservationVersion,
                                  @PathParam("clientLogin") String clientLogin,
                                  @PathParam("reservationUuid") UUID reservationUuid) throws BaseAppException {
        RemoveClientReservationDto reservationDto = new RemoveClientReservationDto(reservationVersion, reservationUuid.toString(), clientLogin);
        reservationEndpointLocal.removeClientReservation(reservationDto);
    }


}
