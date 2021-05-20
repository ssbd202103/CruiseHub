package pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers;

import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDateTime;

import static pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.TokenWrapper.TOKEN_CONSTRAINT;

@Entity(name = "used_tokens")
@NamedQueries({
        @NamedQuery(name = "TokenWrapper.findUsed", query = "SELECT ut FROM used_tokens ut WHERE ut.used=true"),
        @NamedQuery(name = "TokenWrapper.findUnUsed", query = "SELECT ut FROM used_tokens ut WHERE ut.used=false"),
        @NamedQuery(name = "TokenWrapper.findByToken", query = "SELECT ut FROM used_tokens ut WHERE ut.token=:token"),
})
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "token", name = TOKEN_CONSTRAINT),
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenWrapper {
    public static final String TOKEN_CONSTRAINT = "used_tokens_token_unique_constraint";

    @Getter
    @Id
    @SequenceGenerator(name = "USED_TOKENS_SEQ_GEN", sequenceName = "used_tokens_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USED_TOKENS_SEQ_GEN")
    private Long id;

    @Getter
    @Column(name = "token", updatable = false, nullable = false, unique = true)
    private String token;

    @Getter
    @Column(name = "creation_date_time", updatable = false, nullable = false)
    private LocalDateTime creationDateTime;

    @Column(name = "used", nullable = false)
    private Boolean used;

    @JoinColumn(name = "account_id", updatable = false, nullable = false)
    @OneToOne(cascade = {CascadeType.PERSIST})
    @Valid
    private Account account;

    @PrePersist
    private void prePersist() {
        creationDateTime = LocalDateTime.now();
    }
}
