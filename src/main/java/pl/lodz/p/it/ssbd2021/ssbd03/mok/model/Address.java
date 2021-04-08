package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import lombok.Getter;
import lombok.Setter;
import validators.City;
import validators.Country;
import validators.PostCode;
import validators.Street;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity(name = "addresses")
public class Address extends EntityDetails {
    @Getter
    @Id
    @SequenceGenerator(name = "ADDRESS_SEQ_GEN", sequenceName = "address_id_seq")
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

}
