package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.EntityDetails;
import validators.PhoneNumber;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "business_workers")
@DiscriminatorValue("BusinessWorker")
public class BusinessWorker extends AccessLevel {
    @Getter
    @Setter
    @PhoneNumber
    @Column(name = "phone_number")
    private String phoneNumber;

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
        return AccessLevelType.BUSINESS_WORKER;
    }
}