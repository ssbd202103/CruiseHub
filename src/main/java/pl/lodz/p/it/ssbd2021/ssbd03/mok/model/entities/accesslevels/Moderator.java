package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.entities.accesslevels;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevelType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "moderators")
@DiscriminatorValue("Moderator")
public class Moderator extends AccessLevel {

    @Override
    public AccessLevelType getAccessLevelType() {
        return AccessLevelType.MODERATOR;
    }

    public Moderator() {
    }

    public Moderator(boolean enabled) {
        this.enabled = enabled;
    }
}