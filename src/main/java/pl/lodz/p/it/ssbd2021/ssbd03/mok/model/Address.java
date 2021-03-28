package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "addresses")
public class Address  {
    @Id
    private Long id;

    @Column(name = "house_number")
    private Long houseNumber;

    private String street;

    @Column(name = "postal_code")
    private String postalCode;

    private String city;

    private String country;

    @Embedded
    private EntityDetails entityDetails;
}
