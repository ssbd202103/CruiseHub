package pl.lodz.p.it.ssbd2021.ssbd03.mow.model;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.common.BaseEntity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Entity (name = "attractions")
public class Attraction extends BaseEntity {

    @Getter
    @Id
    @SequenceGenerator(name = "ACCOUNT_SEQ_GEN", sequenceName = "account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GEN")
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @NotNull
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @NotNull
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @NotNull
    @Column(name = "price")
    private double price;

    @Getter
    @Setter
    @NotNull
    @Column(name = "number_of_seats")
    private Long numberOfSeats;

    @Getter
    @Setter
    @NotNull
    @Column(name = "available")
    private Boolean available;

    @Getter
    @Setter
    @NotNull
    @OneToOne
    @JoinColumn(name = "cruise_id")
    private CruiseGroup cruise;

}

