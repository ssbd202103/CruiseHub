package pl.lodz.p.it.ssbd2021.ssbd03.exceptions.status_mappers;

import javax.ejb.EJBException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.APP_INTERNAL_SERVER_ERROR;

@Provider
public class EJBExceptionMapper implements ExceptionMapper<EJBException> {
    @Override
    public Response toResponse(EJBException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(APP_INTERNAL_SERVER_ERROR).build();
    }
}
