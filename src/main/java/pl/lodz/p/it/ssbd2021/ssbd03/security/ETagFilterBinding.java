package pl.lodz.p.it.ssbd2021.ssbd03.security;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Adnotacja udosępniająca możliwość filtrowania ETagu
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface ETagFilterBinding {
}
