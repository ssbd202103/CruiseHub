package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels;

import lombok.Data;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.EntityDetails;

import javax.persistence.*;

@Data
@Entity(name = "business_workers")
@DiscriminatorValue("BusinessWorker")
public class BusinessWorker extends AccessLevel {
    @Column(name = "phone_number")
    private String phoneNumber;

    @Embedded
    private EntityDetails entityDetails;

    @Version
    private Long version;

    @Override
    public AccessLevelType getAccessLevelType() {
        return AccessLevelType.BUSINESS_WORKER;
    }
}