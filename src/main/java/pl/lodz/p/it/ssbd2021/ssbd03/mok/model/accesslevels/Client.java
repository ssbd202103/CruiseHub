package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels;

import lombok.Data;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.EntityDetails;
import validators.PhoneNumber;

import javax.persistence.*;

@Data
@Entity(name = "clients")
@DiscriminatorValue("Client")
public class Client extends AccessLevel {
    @OneToOne
    @JoinColumn(name = "home_address_id")
    private Address homeAddress;

    @PhoneNumber
    @Column(name = "phone_number")
    private String phoneNumber;

    @Embedded
    private EntityDetails entityDetails;

    @Override
    public AccessLevelType getAccessLevelType() {
        return AccessLevelType.CLIENT;
    }
}
