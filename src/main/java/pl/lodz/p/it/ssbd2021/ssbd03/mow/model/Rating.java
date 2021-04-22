package pl.lodz.p.it.ssbd2021.ssbd03.mow.model;


import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.entities.Account;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @NotNull
    @Column(name = "rating")
    private double rating;

    public Rating(Long id, @NotNull Account account, @NotNull Cruise cruise, @NotNull double rating) {
        this.id = id;
        this.account = account;
        this.cruise = cruise;
        this.rating = rating;
    }

    public Rating() {
    }
}
