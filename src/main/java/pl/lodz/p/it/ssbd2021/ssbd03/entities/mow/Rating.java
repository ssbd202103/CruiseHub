package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;


import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

@Entity(name = "ratings")
@NamedQueries({
        @NamedQuery(name= "Rating.findByCruiseGroupName", query = "SELECT r FROM ratings r WHERE r.cruiseGroup.name =:name"),
        @NamedQuery(name= "Rating.findByCruiseGroupNameAndAccountLogin", query = "SELECT r FROM ratings r WHERE r.cruiseGroup.name=:name AND r.account.login=:login")
})

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
    @JoinColumn(name = "cruise_group_id")
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    private CruiseGroup cruiseGroup;

    @Getter
    @Setter
    @Min(value = 1, message = RATING_CONSTRAINT_ERROR)
    @Max(value = 5, message = RATING_CONSTRAINT_ERROR)
    @Column(name = "rating")
    private Integer rating;

    public Rating(@NotNull(message = CONSTRAINT_NOT_NULL) Account account,
                  @NotNull(message = CONSTRAINT_NOT_NULL) CruiseGroup cruiseGroup, @
                          NotNull(message = CONSTRAINT_NOT_NULL) Integer rating) {
        this.account = account;
        this.cruiseGroup = cruiseGroup;
        this.rating = rating;
    }

    public Rating() {
    }
}
