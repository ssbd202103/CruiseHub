package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "access_level", discriminatorType = DiscriminatorType.STRING)
@Entity(name = "access_levels")
public class AccessLevel extends EntityDetails {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public AccessLevelType getAccessLevelType() {
        return null; // todo
    }
}