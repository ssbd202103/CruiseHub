package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.entities;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.common.validators.City;
import pl.lodz.p.it.ssbd2021.ssbd03.common.validators.Country;
import pl.lodz.p.it.ssbd2021.ssbd03.common.validators.PostCode;
import pl.lodz.p.it.ssbd2021.ssbd03.common.validators.Street;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity(name = "addresses")
public class Address extends BaseEntity {
    @Getter
    @Id
    @SequenceGenerator(name = "ADDRESS_SEQ_GEN", sequenceName = "address_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESS_SEQ_GEN")
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @Positive
    @Column(name = "house_number")
    private Long houseNumber;

    @Getter
    @Setter
    @Street
    private String street;

    @Getter
    @Setter
    @PostCode
    @Column(name = "postal_code")
    private String postalCode;

    @Getter
    @Setter
    @City
    private String city;

    @Getter
    @Setter
    @Country
    private String country;

    public Address() {
    }

    public Address(Long houseNumber, String street, String postalCode, String city, String country) {
        this.houseNumber = houseNumber;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }
}
