package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import validators.City;
import validators.Country;
import validators.PostCode;
import validators.Street;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Positive;

@Entity(name = "addresses")
public class Address {
    @Id
    private Long id;

    @Positive
    @Column(name = "house_number")
    private Long houseNumber;

    @Street
    private String street;

    @PostCode
    @Column(name = "postal_code")
    private String postalCode;

    @City
    private String city;

    @Country
    private String country;

    @Embedded
    private EntityDetails entityDetails;
}
