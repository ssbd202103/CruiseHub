package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity(name = "addresses")
@TableGenerator(name="AddressIdGenerator", table="generator", pkColumnName="generator_key", valueColumnName="generator_value", pkColumnValue="AddressId")
public class Address  {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="AddressIdGenerator")
    @Column(name = "id")
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
