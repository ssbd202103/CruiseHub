package pl.lodz.p.it.ssbd2021.ssbd03.entities.mok;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.City;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Country;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PostCode;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Street;

import javax.persistence.*;
import javax.validation.constraints.Positive;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE_ERROR;

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
    @Positive(message = CONSTRAINT_POSITIVE_ERROR)
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
