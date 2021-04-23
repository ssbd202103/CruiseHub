package pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "clients")
@DiscriminatorValue("Client")
public class Client extends AccessLevel {

    @Getter
    @NotNull
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "home_address_id")
    private Address homeAddress;

    @Getter
    @Setter
    @PhoneNumber
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Override
    public AccessLevelType getAccessLevelType() {
        return AccessLevelType.CLIENT;
    }

    public Client() {
    }

    public Client(Address homeAddress, String phoneNumber, boolean enabled) {
        this.homeAddress = homeAddress;
        this.phoneNumber = phoneNumber;
        this.enabled = enabled;
    }
}