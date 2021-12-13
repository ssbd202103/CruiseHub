package pl.lodz.p.it.ssbd2021.ssbd03.controllers.healthcheck;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@Liveness
public class AppHealthCheck implements HealthCheck {
    @Inject
    private HealthStatus healthStatus;

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("is-app-up").status(healthStatus.isLive())
                .build();
    }
}
