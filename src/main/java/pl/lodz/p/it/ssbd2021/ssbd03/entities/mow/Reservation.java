package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;


import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

    //TODO sprawdzenie i powprawnienie Reservation.findWorkerCruiseReservations query
@NamedQueries({
        @NamedQuery(name = "Reservation.findByUUID", query = "SELECT reservation FROM reservations reservation WHERE reservation.uuid = :uuid"),
        @NamedQuery(name = "Reservation.findCruiseReservations", query = "SELECT reservation FROM reservations reservation WHERE reservation.cruise = :id"),
        @NamedQuery(name = "Reservation.findByUUIDAndLogin", query = "SELECT res FROM reservations res WHERE res.uuid=:uuid AND res.client.account.login=:login")
        //todo change query so you can get reserwation that is in the criuse that is in the cruise group that i sowned by company that the Buissner worker is working for
        // @NamedQuery(name = "Reservation.findWorkerCruiseReservations", query = "SELECT reservation FROM reservations reservation WHERE reservation.cruise = :id and reservation.cruise IN (SELECT id FROM cruises WHERE cruises_groups.id IN (SELECT id FROM cruises_groups WHERE company.id IN (SELECT id FROM companies WHERE companies.id IN (select business_workers.company FROM business_workers WHERE id = :id))) ) ")
})
@Entity(name = "reservations")
public class Reservation extends BaseEntity {

    @Getter
    @Id
    @SequenceGenerator(name = "RESERVATION_SEQ_GEN", sequenceName = "reservations_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESERVATION_SEQ_GEN")
    @Column(name = "id")
    private long id;

    @Getter
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
    private UUID uuid;

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
    @Setter
    @Positive(message = CONSTRAINT_POSITIVE_ERROR)
    @Column(name = "price")
    private Double price;

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
