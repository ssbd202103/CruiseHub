package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;


import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity(name = "cruise_addresses")
public class CruiseAddress extends BaseEntity {

    @Getter
    @Id
    @SequenceGenerator(name = "CRUISE_ADDRESS_SEQ_GEN", sequenceName = "cruise_addresses_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRUISE_ADDRESS_SEQ_GEN")
    @Column(name = "id")
    private Integer id;

    @Getter
    @Setter
    @NotNull
    @Column(name = "street")
    private String street;

    @Getter
    @Setter
    @NotNull
    @Column(name = "street_number")
    private Integer streetNumber;

    @Getter
    @Setter
    @NotNull
    @Column(name = "harbor_name")
    private String harborName;

    @Getter
    @Setter
    @NotNull
    @Column(name = "city_name")
    private String city_name;

    @Getter
    @Setter
    @NotNull
    @Column(name = "country_name")
    private String country_name;


    public CruiseAddress(Integer id, @NotNull String street, @NotNull Integer streetNumber, @NotNull String harborName, @NotNull String city_name, @NotNull String country_name) {
        this.id = id;
        this.street = street;
        this.streetNumber = streetNumber;
        this.harborName = harborName;
        this.city_name = city_name;
        this.country_name = country_name;
    }

    public CruiseAddress() {
    }
}

