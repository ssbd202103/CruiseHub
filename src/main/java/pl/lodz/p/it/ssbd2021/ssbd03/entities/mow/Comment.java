package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity(name = "comments")
public class Comment extends BaseEntity {
    @Getter
    @Id
    @SequenceGenerator(name = "COMMENT_SEQ_GEN", sequenceName = "comments_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENT_SEQ_GEN")
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @NotNull
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Getter
    @Setter
    @NotEmpty
    @Column(name = "comment")
    private String content;

    @Getter
    @Setter
    @NotNull
    @OneToOne
    @JoinColumn(name = "cruise_id")
    private Cruise cruise;

    public Comment(Account account, String content, Cruise cruise) {
        this.account = account;
        this.content = content;
        this.cruise = cruise;
    }

    public Comment() {
    }
}



