package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels;

import lombok.Data;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.EntityDetails;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Data
@Entity(name = "moderators")
@DiscriminatorValue("Moderator")
public class Moderator extends AccessLevel {

    @Embedded
    private EntityDetails entityDetails;

    @Override
    public AccessLevelType getAccessLevelType() {
        return AccessLevelType.MODERATOR;
    }
}