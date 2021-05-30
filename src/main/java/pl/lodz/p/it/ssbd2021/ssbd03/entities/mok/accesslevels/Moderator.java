package pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels;

import lombok.ToString;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "moderators")
@DiscriminatorValue("Moderator")
@ToString
public class Moderator extends AccessLevel {

    @Override
    public AccessLevelType getAccessLevelType() {
        return AccessLevelType.MODERATOR;
    }

    public Moderator() {
        this.enabled = false;
    }

    public Moderator(boolean enabled) {
        this.enabled = enabled;
    }
}