package pl.lodz.p.it.ssbd2021.ssbd03.security;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@ETagFilterBinding
public class ETagFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String header = requestContext.getHeaderString("If-Match");
        if(header == null || header.isEmpty()) {
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
        } else if(!EntityIdentitySignerVerifier.validateEntitySignature(header)){
            requestContext.abortWith(Response.status(Response.Status.PRECONDITION_FAILED).build());
        }
    }
}
