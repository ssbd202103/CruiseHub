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
public class Client extends EntityDetails implements AccessLevel {
    @Id
    private Long id;

    private boolean active;

    @Getter
    @Setter
    @NotNull
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "home_address_id")
    private Address homeAddress;

    @Getter
    @Setter
    @PhoneNumber
    @Column(name = "phone_number")
    private String phoneNumber;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public AccessLevelType getAccessLevelType() {
        return AccessLevelType.CLIENT;
    }

}
