package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.EntityDetails;

import javax.persistence.*;

@Entity(name = "administrators")
@DiscriminatorValue("Administrator")
public class Administrator extends EntityDetails implements AccessLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Override
    public AccessLevelType getAccessLevelType() {
        return AccessLevelType.ADMINISTRATOR;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public void setAccessLevelType(AccessLevelType accessLevelType) {

    }
}