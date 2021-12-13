package pl.lodz.p.it.ssbd2021.ssbd03.controllers.healthcheck;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import static java.lang.String.valueOf;
@ApplicationScoped
@Path("/kill")
public class HealthKillerController {
    @Inject
    private HealthStatus healthStatus;

    @GET
    @Path("/health")
    public String toggle() {
        healthStatus.setLive(!healthStatus.isLive());
        return valueOf(healthStatus.isLive());
    }
}
