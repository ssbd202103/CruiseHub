package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;


import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE_OR_ZERO_ERROR;

@Entity(name = "ratings")
public class Rating extends BaseEntity {

    @Getter
    @Id
    @SequenceGenerator(name = "RATING_SEQ_GEN", sequenceName = "ratings_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RATING_SEQ_GEN")
    @Column(name = "id")
    private long id;

    @Getter
    @OneToOne
    @JoinColumn(name = "account_id")
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    private Account account;

    @Getter
    @OneToOne
    @JoinColumn(name = "cruise_id")
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    private Cruise cruise;

    @Getter
    @Setter
    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO_ERROR)
    @Column(name = "rating")
    private double rating;

    public Rating(@NotNull(message = CONSTRAINT_NOT_NULL) Account account, @NotNull(message = CONSTRAINT_NOT_NULL) Cruise cruise, @NotNull(message = CONSTRAINT_NOT_NULL) double rating) {
        this.account = account;
        this.cruise = cruise;
        this.rating = rating;
    }

    public Rating() {
    }
}
