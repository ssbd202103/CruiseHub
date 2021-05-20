package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;


import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity(name = "ratings")
public class Rating extends BaseEntity {

    @Getter
    @Id
    @SequenceGenerator(name = "RATING_SEQ_GEN", sequenceName = "ratings_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RATING_SEQ_GEN")
    @Column(name = "id")
    private Long id;

    @Getter
    @NotNull
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Getter
    @NotNull
    @OneToOne
    @JoinColumn(name = "cruise_id")
    private Cruise cruise;

    @Getter
    @Setter
    @PositiveOrZero
    @Column(name = "rating")
    private double rating;

    public Rating(@NotNull Account account, @NotNull Cruise cruise, @NotNull double rating) {
        this.account = account;
        this.cruise = cruise;
        this.rating = rating;
    }

    public Rating() {
    }
}
