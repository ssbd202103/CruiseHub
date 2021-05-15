package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;


import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "reservations")
public class Reservation extends BaseEntity {

    @Getter
    @Id
    @SequenceGenerator(name = "RESERVATION_SEQ_GEN", sequenceName = "reservations_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESERVATION_SEQ_GEN")
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @PositiveOrZero
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
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "reservation_attractions",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "attraction_id")
    )
    private final List<Attraction> attractions = new ArrayList<>();

    public Reservation(Long numberOfSeats, Cruise cruise, Client client) {
        this.numberOfSeats = numberOfSeats;
        this.cruise = cruise;
        this.client = client;
    }

    public Reservation() {
    }
}
