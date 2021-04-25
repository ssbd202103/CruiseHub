package pl.lodz.p.it.ssbd2021.ssbd03.security;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Klasa odpowiadający za filtrowanie nagłówka
 */
@Provider
@ETagFilterBinding
public class ETagFilter implements ContainerRequestFilter {
    /**
     * Funkcja sprawdzająca poprawność pobranego nagłówka
     * @param requestContext zmiena z której pobierany jest nagłówek
     */
    @Override
    public void filter(ContainerRequestContext requestContext)  {
        String header = requestContext.getHeaderString("If-Match");
        if(header == null || header.isEmpty()) {
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
        } else if(!EntityIdentitySignerVerifier.validateEntitySignature(header)){
            requestContext.abortWith(Response.status(Response.Status.PRECONDITION_FAILED).build());
        }
    }
}
