package pl.lodz.p.it.ssbd2021.ssbd03.app.config;

import org.glassfish.jersey.server.ResourceConfig;


public class CruiseHubConfig extends ResourceConfig {

    public CruiseHubConfig() {
        register(MarshallingFeature.class);
    }
}