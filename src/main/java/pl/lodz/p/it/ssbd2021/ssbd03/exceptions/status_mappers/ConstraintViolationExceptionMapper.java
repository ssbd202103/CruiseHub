package pl.lodz.p.it.ssbd2021.ssbd03.exceptions.status_mappers;

import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.ErrorMessage;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_VIOLATION_ERROR;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        ListMultimap<String, String> multimap = MultimapBuilder.ListMultimapBuilder.hashKeys().arrayListValues().build();

        exception.getConstraintViolations().forEach(
                cv -> multimap.put(
                        Iterables.getLast(cv.getPropertyPath()).getName(), cv.getMessage()
                )
        );

        return Response.status(BAD_REQUEST).entity(new ErrorMessage(CONSTRAINT_VIOLATION_ERROR, multimap.asMap())).build();
    }
}
