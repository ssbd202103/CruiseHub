package pl.lodz.p.it.ssbd2021.ssbd03.mow.model;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.common.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "cruises_groups")
public class CruiseGroup extends BaseEntity {

    @Getter
    @Id
    @SequenceGenerator(name = "ACCOUNT_SEQ_GEN", sequenceName = "account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GEN")
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id")
    private Company company;


    @Getter
    @Setter
    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "start_address_id")
    private CruiseAddress address;


    @Getter
    @Setter
    @NotNull
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @NotNull
    @Column(name = "number_of_seats")
    private Long numberOfSeats;

    @Getter
    @Setter
    @NotNull
    @Column(name = "price")
    private BigDecimal price;

    @Getter
    @NotNull
    @ManyToMany
    @JoinTable(name = "cruises_groups_pictures")
    private List<CruisePicture> cruisePicture;

    @Getter
    @Setter
    @NotNull
    @Column(name = "average_rating")
    private BigDecimal averageRating;


    public CruiseGroup(Long id, @NotNull Company company, @NotNull CruiseAddress address, @NotNull String name, @NotNull Long numberOfSeats, @NotNull BigDecimal price, @NotNull List<CruisePicture> cruisePicture, @NotNull BigDecimal averageRating) {
        this.id = id;
        this.company = company;
        this.address = address;
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.price = price;
        this.cruisePicture = cruisePicture;
        this.averageRating = averageRating;
    }

    public CruiseGroup() {
    }
}


