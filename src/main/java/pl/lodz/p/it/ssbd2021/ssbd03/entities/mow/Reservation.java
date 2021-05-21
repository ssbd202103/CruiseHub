package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;


import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE_OR_ZERO_ERROR;

@Entity(name = "reservations")
public class Reservation extends BaseEntity {

    @Getter
    @Id
    @SequenceGenerator(name = "RESERVATION_SEQ_GEN", sequenceName = "reservations_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESERVATION_SEQ_GEN")
    @Column(name = "id")
    private long id;

    @Getter
    @Setter
    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO_ERROR)
    @Column(name = "number_of_seats")
    private long numberOfSeats;

    @Getter
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "cruise_id")
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    private Cruise cruise;

    @Getter
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "client_id")
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    private Client client;

    @Getter
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "reservation_attractions",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "attraction_id")
    )
    private final List<Attraction> attractions = new ArrayList<>();

    public Reservation(long numberOfSeats, Cruise cruise, Client client) {
        this.numberOfSeats = numberOfSeats;
        this.cruise = cruise;
        this.client = client;
    }

    public Reservation() {
    }
}
