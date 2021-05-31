package pl.lodz.p.it.ssbd2021.ssbd03.security;

import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Klasa rozwiazujaca problemy z CORSem
 */
@Provider
public class CORSFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", PropertiesReader.getSecurityProperties().getProperty("app.frontend.url"));
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
}
