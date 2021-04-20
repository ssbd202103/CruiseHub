package pl.lodz.p.it.ssbd2021.ssbd03.mow.model;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.entities.Account;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "comments")
public class Comment extends BaseEntity {
    @Getter
    @Id
    @SequenceGenerator(name = "ACCOUNT_SEQ_GEN", sequenceName = "account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GEN")
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
    @NotNull
    @Column(name = "comment")
    private String content;

    @Getter
    @Setter
    @NotNull
    @OneToOne
    @JoinColumn(name = "cruise_id")
    private Cruise cruise;


    public Comment(Long id, @NotNull Account account, @NotNull String content, @NotNull Cruise cruise) {
        this.id = id;
        this.account = account;
        this.content = content;
        this.cruise = cruise;
    }

    public Comment() {
    }
}



