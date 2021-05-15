package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;


import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.City;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Country;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Street;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;


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
    @Street
    @Column(name = "street")
    private String street;

    @Getter
    @Setter
    @Positive
    @Column(name = "street_number")
    private Integer streetNumber;

    @Getter
    @Setter
    @NotEmpty
    @Column(name = "harbor_name")
    private String harborName;

    @Getter
    @Setter
    @City
    @Column(name = "city_name")
    private String cityName;

    @Getter
    @Setter
    @Country
    @Column(name = "country_name")
    private String countryName;

    public CruiseAddress(String street, Integer streetNumber, String harborName, String cityName, String countryName) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.harborName = harborName;
        this.cityName = cityName;
        this.countryName = countryName;
    }

    public CruiseAddress() {
    }
}

