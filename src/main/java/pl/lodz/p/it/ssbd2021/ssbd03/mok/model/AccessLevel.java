package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "access_level", discriminatorType = DiscriminatorType.STRING)
@Entity(name = "access_levels")
public abstract class AccessLevel {
    @Getter
    @Id
    @SequenceGenerator(name = "ACCESSLEVEL_SEQ_GEN", sequenceName = "accesslevel_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCESSLEVEL_SEQ_GEN")
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    private boolean active;

    public abstract AccessLevelType getAccessLevelType();
}