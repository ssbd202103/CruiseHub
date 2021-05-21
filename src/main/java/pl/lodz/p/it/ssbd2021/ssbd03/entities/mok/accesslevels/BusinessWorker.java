package pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;

@Entity(name = "business_workers")
@DiscriminatorValue("BusinessWorker")
public class BusinessWorker extends AccessLevel {

    @Getter
    @Setter
    @PhoneNumber
    @Column(name = "phone_number", nullable = false)
    @NotNull(message = CONSTRAINT_NOT_EMPTY)
    private String phoneNumber;

    @Getter
    @Setter
    @Column(name = "confirmed_by_business_worker", nullable = false)
    private boolean confirmedByBusinessWorker;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(updatable = false, nullable = false, name = "company_id")
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
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