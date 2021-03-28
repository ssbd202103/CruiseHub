package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.EntityDetails;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
//@DiscriminatorValue("BusinessWorker")
public class BusinessWorker extends AccessLevel {
    private String phoneNumber;


    @Override
    public AccessLevelType getAccessLevelType() {
        return AccessLevelType.BUSINESS_WORKER;
    }
}