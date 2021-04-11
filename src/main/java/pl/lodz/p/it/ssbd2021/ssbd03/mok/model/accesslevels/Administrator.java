package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevelType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "administrators")
@DiscriminatorValue("Administrator")
public class Administrator extends AccessLevel {

    @Override
    public AccessLevelType getAccessLevelType() {
        return AccessLevelType.ADMINISTRATOR;
    }

    public Administrator() {
    }

    public Administrator(boolean enabled) {
        this.enabled = enabled;
    }

}