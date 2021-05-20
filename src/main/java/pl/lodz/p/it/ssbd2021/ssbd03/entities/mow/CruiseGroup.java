package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

@Entity(name = "cruises_groups")
public class CruiseGroup extends BaseEntity {

    @Getter
    @Id
    @SequenceGenerator(name = "CRUISES_GROUP_SEQ_GEN", sequenceName = "cruises_groups_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRUISES_GROUP_SEQ_GEN")
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id")
    @Valid
    private Company company;


    @Getter
    @Setter
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "start_address_id")
    @Valid
    private CruiseAddress address;

    @Getter
    @Setter
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO_ERROR)
    @Column(name = "number_of_seats")
    private Long numberOfSeats;

    @Getter
    @Setter
    @Positive(message = CONSTRAINT_POSITIVE_ERROR)
    @Column(name = "price")
    private Double price;

    @Getter
    @ManyToMany
    @JoinTable(name = "cruises_group_pictures",
            joinColumns = @JoinColumn(name = "cruise_picture_id"),
            inverseJoinColumns = @JoinColumn(name = "cruises_group_id")
    )
    @Valid
    private final List<CruisePicture> cruisePictures = new ArrayList<>();

    @Getter
    @Setter
    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO_ERROR)
    @Column(name = "average_rating")
    private Double averageRating;


    public CruiseGroup(Company company, CruiseAddress address, String name, Long numberOfSeats,
                       Double price, Double averageRating) {
        this.company = company;
        this.address = address;
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.price = price;
        this.averageRating = averageRating;
    }

    public CruiseGroup() {
    }
}


