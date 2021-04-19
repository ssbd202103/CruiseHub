package pl.lodz.p.it.ssbd2021.ssbd03.mow.model;


import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.common.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity(name = "cruise_addresses")
public class CruiseAddress extends BaseEntity {

    @Getter
    @Id
    @SequenceGenerator(name = "ACCOUNT_SEQ_GEN", sequenceName = "account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GEN")
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
    @Column(name = "city")
    private String city;

    @Getter
    @Setter
    @NotNull
    @Column(name = "country")
    private String country;
}

