package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.EntityDetails;
import validators.PhoneNumber;

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
        return AccessLevelType.CLIENT;
    }
}
