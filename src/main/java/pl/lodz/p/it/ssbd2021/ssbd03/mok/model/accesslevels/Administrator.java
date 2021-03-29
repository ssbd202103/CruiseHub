package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels;

import lombok.Data;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.EntityDetails;

import javax.persistence.*;

@Data
@Entity(name = "administrators")
@DiscriminatorValue("Administrator")
public class Administrator extends AccessLevel {

    @Embedded
    private EntityDetails entityDetails;

    @Override
    public AccessLevelType getAccessLevelType() {
        return AccessLevelType.ADMINISTRATOR;
    }
}