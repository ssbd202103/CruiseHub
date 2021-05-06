package pl.lodz.p.it.ssbd2021.ssbd03.app.config;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

public class MarshallingFeature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {
        context.register(AppJsonProvider.class, MessageBodyReader.class, MessageBodyWriter.class);
        return true;
    }
}
