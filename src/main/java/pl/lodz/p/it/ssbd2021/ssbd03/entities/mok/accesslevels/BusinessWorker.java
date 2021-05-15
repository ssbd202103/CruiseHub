package pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.persistence.*;

@Entity(name = "business_workers")
@DiscriminatorValue("BusinessWorker")
public class BusinessWorker extends AccessLevel {

    @Getter
    @Setter
    @PhoneNumber
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Getter
    @Setter
    @Column(name = "confirmed_by_business_worker", nullable = false)
    private Boolean confirmedByBusinessWorker;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(updatable = false, nullable = false, name = "company_id")
    private Company company;

    @Override
    public AccessLevelType getAccessLevelType() {
        return AccessLevelType.BUSINESS_WORKER;
    }

    public BusinessWorker() {
    }

    public BusinessWorker(String phoneNumber, boolean enabled) {
        this.phoneNumber = phoneNumber;
        this.enabled = enabled;
        this.confirmedByBusinessWorker = false;
    }
}