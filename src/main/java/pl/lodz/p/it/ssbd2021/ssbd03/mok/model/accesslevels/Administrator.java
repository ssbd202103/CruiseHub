package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.EntityDetails;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity(name = "administrators")
@DiscriminatorValue("Administrator")
public class Administrator extends AccessLevel {

    @Getter
    @NotNull
    @Embedded
    private EntityDetails entityDetails;

    @Getter
    @Setter
    @Version
    private Long version;


    @Override
    public AccessLevelType getAccessLevelType() {
        return AccessLevelType.ADMINISTRATOR;
    }
}