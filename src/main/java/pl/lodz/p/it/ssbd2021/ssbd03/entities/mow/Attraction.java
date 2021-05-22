package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;


@Entity (name = "attractions")
@NamedQueries({
        @NamedQuery(name = "Attraction.findByName", query = "SELECT att FROM attractions att WHERE att.name = :name"),
        //TODO query that can delete
       // @NamedQuery(name = "Attraction.deleteByName", query = "DELETE FROM attractions WHERE attractions.name= :name"),
        //TODO query któe ma dostęn do reservation_attraction by sprawdzić czy już jakaś atrakcjia jest zarezerwowana
        @NamedQuery(name = "Attraction.findByIdIfReserved", query = "SELECT att FROM attractions att WHERE att.name = :name"),

})

public class Attraction extends BaseEntity {

    @Getter
    @Id
    @SequenceGenerator(name = "ATTRACTION_SEQ_GEN", sequenceName = "attractions_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATTRACTION_SEQ_GEN")
    @Column(name = "id")
    private long id;

    @Getter
    @Setter
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @Positive(message = CONSTRAINT_POSITIVE_ERROR)
    @Column(name = "price")
    private double price;

    @Getter
    @Setter
    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO_ERROR)
    @Column(name = "number_of_seats")
    private long numberOfSeats;

    @Getter
    @Setter
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Column(name = "available")
    private boolean available;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "cruise_id")
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    private CruiseGroup cruise;

    public Attraction(String name, String description,
                      double price, long numberOfSeats, boolean available, CruiseGroup cruise) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.numberOfSeats = numberOfSeats;
        this.available = available;
        this.cruise = cruise;
    }

    public Attraction() {
    }
}

