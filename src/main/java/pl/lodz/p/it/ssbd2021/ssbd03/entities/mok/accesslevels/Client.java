package pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;

@Entity(name = "clients")
@DiscriminatorValue("Client")
@ToString
public class Client extends AccessLevel {

    @Getter
    @Setter
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "home_address_id")
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    private Address homeAddress;

    @Getter
    @Setter
    @PhoneNumber
    @Column(name = "phone_number", nullable = false)
    @NotNull(message = CONSTRAINT_NOT_EMPTY)
    private String phoneNumber;

    @Override
    public AccessLevelType getAccessLevelType() {
        return AccessLevelType.CLIENT;
    }

    public Client() {
        this.enabled = true;
    }

    public Client(Address homeAddress, String phoneNumber) {
        this.homeAddress = homeAddress;
        this.phoneNumber = phoneNumber;
        this.enabled = true;
    }

    public Client(Address homeAddress, String phoneNumber, boolean enabled) {
        this.homeAddress = homeAddress;
        this.phoneNumber = phoneNumber;
        this.enabled = enabled;
    }

    public Client(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.enabled = true;
    }
}
