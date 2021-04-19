package pl.lodz.p.it.ssbd2021.ssbd03.mow.model;


import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.entities.accesslevels.Client;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "reservations")
public class Reservation extends BaseEntity {

    @Getter
    @Id
    @SequenceGenerator(name = "ACCOUNT_SEQ_GEN", sequenceName = "account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GEN")
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @NotNull
    @Column(name = "number_of_seats")
    private Long numberOfSeats;

    @Getter
    @NotNull
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "cruise_id")
    private Cruise cruise;

    @Getter
    @NotNull
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "client_id")
    private Client client;

    @Getter
    @NotNull
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "reservations_attractions")
    private List<Attraction> attractions;
}
