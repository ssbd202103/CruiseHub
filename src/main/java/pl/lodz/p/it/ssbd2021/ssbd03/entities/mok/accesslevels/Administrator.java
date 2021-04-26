package pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;

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
        this.enabled = false;
    }

    public Administrator(boolean enabled) {
        this.enabled = enabled;
    }

}