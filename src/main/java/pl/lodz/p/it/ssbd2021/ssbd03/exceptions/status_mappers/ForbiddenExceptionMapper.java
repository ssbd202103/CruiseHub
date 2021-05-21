package pl.lodz.p.it.ssbd2021.ssbd03.exceptions.status_mappers;


import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.ForbiddenException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> { // zeby nie doszlo do naduzyc bedziemy recznie rzucac
    @Override
    public Response toResponse(ForbiddenException exception) {
        return Response.status(Response.Status.FORBIDDEN).entity(exception.getMessage()).build();
    }
}
