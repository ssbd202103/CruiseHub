package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "access_level", discriminatorType = DiscriminatorType.STRING)
@Entity(name = "access_levels")
public abstract class AccessLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean active;

    public abstract AccessLevelType getAccessLevelType();
}