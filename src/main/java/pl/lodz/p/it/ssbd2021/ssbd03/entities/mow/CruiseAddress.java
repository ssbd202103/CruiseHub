package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.City;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Country;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Street;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE;


@Entity(name = "cruise_addresses")
@ToString
public class CruiseAddress extends BaseEntity {

    @Getter
    @Id
    @SequenceGenerator(name = "CRUISE_ADDRESS_SEQ_GEN", sequenceName = "cruise_addresses_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRUISE_ADDRESS_SEQ_GEN")
    @ToString.Exclude
    private long id;

    @Getter
    @Setter
    @Street
    @Column(name = "street")
    private String street;

    @Getter
    @Setter
    @Positive(message = CONSTRAINT_POSITIVE)
    @Column(name = "street_number")
    private int streetNumber;

    @Getter
    @Setter
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
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

    public CruiseAddress(String street, int streetNumber, String harborName, String cityName, String countryName) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.harborName = harborName;
        this.cityName = cityName;
        this.countryName = countryName;
    }

    public CruiseAddress() {
    }

    @Override
    public Long getIdentifier() {
        return id;
    }
}

