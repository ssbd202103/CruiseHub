package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

@Entity(name = "attractions")
@NamedQueries({
        @NamedQuery(name = "Attraction.findByName", query = "SELECT att FROM attractions att WHERE att.name = :name"),
        @NamedQuery(name = "Attraction.findByIdIfReserved", query = "SELECT att FROM attractions att WHERE att.name = :name"),

})
@ToString
public class Attraction extends BaseEntity {

    @Getter
    @Id
    @SequenceGenerator(name = "ATTRACTION_SEQ_GEN", sequenceName = "attractions_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATTRACTION_SEQ_GEN")
    @ToString.Exclude
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
    @Column(name = "has_free_spots")
    private boolean hasFreeSpots;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "cruise_id")
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    @ToString.Exclude
    private CruiseGroup cruise;

    public Attraction(String name, String description,
                      double price, long numberOfSeats, boolean hasFreeSpots, CruiseGroup cruise) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.numberOfSeats = numberOfSeats;
        this.hasFreeSpots = hasFreeSpots;
        this.cruise = cruise;
    }

    public Attraction(String name, String description, double price, long numberOfSeats, CruiseGroup cruise) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.numberOfSeats = numberOfSeats;
        this.cruise = cruise;
        this.hasFreeSpots = true;
    }

    public Attraction() {
    }

    @Override
    public Long getIdentifier() {
        return id;
    }
}

