package pl.lodz.p.it.ssbd2021.ssbd03.controllers.healthcheck;

import lombok.Data;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Data
public class HealthStatus {
    private boolean live = true;
}