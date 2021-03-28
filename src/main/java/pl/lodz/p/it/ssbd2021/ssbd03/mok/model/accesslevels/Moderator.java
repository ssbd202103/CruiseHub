package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.EntityDetails;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

//@Entity
//@DiscriminatorValue("Moderator")
//public class Moderator extends AccessLevel {
//
//    @Override
//    public AccessLevelType getAccessLevelType() {
//        return AccessLevelType.MODERATOR;
//    }
//}