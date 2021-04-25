package pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;

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
        this.enabled = false;
    }
}