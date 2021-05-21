package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;

@Entity(name = "comments")
public class Comment extends BaseEntity {
    @Getter
    @Id
    @SequenceGenerator(name = "COMMENT_SEQ_GEN", sequenceName = "comments_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENT_SEQ_GEN")
    @Column(name = "id")
    private long id;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "account_id")
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    private Account account;

    @Getter
    @Setter
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    @Column(name = "comment")
    private String content;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "cruise_id")
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    private Cruise cruise;

    public Comment(Account account, String content, Cruise cruise) {
        this.account = account;
        this.content = content;
        this.cruise = cruise;
    }

    public Comment() {
    }
}



