package pl.lodz.p.it.ssbd2021.ssbd03.security;

import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.interceptor.Interceptors;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.PRECONDITION_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.ETAG_EMPTY_ERROR;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.ETAG_INVALID_ERROR;

/**
 * Klasa odpowiadający za filtrowanie nagłówka
 */
@Provider
@ETagFilterBinding
public class ETagFilter implements ContainerRequestFilter {
    /**
     * Funkcja sprawdzająca poprawność pobranego nagłówka
     *
     * @param requestContext zmienna z której pobierany jest nagłówek
     */
    @Override
    public void filter(ContainerRequestContext requestContext) {
        String header = requestContext.getHeaderString("If-Match");
        if (header == null || header.isEmpty()) {
            requestContext.abortWith(Response.status(BAD_REQUEST).entity(ETAG_EMPTY_ERROR).build());
        } else if (!EntityIdentitySignerVerifier.validateEntitySignature(header)) {
            requestContext.abortWith(Response.status(PRECONDITION_FAILED).entity(ETAG_INVALID_ERROR).build());
        }
    }
}
