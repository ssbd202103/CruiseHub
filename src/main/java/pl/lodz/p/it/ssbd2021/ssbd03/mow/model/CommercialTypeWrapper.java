package pl.lodz.p.it.ssbd2021.ssbd03.mow.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "commercial_type")
public class CommercialTypeWrapper {
    @Getter
    @Id
    @SequenceGenerator(name = "ACCOUNT_SEQ_GEN", sequenceName = "account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GEN")
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    @Setter
    private CommercialType commercialType;
}
