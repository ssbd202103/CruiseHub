package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Positive;
import javax.persistence.SequenceGenerator;
import javax.persistence.TableGenerator;
import javax.persistence.*;

@Entity(name = "addresses")
public class Address {
    @Id
    @SequenceGenerator(name = "ADDRESS_SEQ_GEN", sequenceName = "address_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ADDRESS_SEQ_GEN")
    @Column(name = "id")
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

    @Version
    private Long version;
}
