package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "access_level", discriminatorType = DiscriminatorType.STRING)
@Entity(name = "access_levels")
public interface AccessLevel {
    @Id
    @SequenceGenerator(name = "ACCESSLEVEL_SEQ_GEN", sequenceName = "accesslevel_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCESSLEVEL_SEQ_GEN")
    @Column(name = "id")
    Long getId();

    void setId(Long id);

    boolean isActive();

    void setActive(boolean active);

    @Transient
    AccessLevelType getAccessLevelType();
}