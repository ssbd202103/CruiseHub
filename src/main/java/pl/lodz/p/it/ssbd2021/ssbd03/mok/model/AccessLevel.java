package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import javax.persistence.*;

//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "access_level", discriminatorType = DiscriminatorType.STRING)
@Entity(name = "access_levels")
public interface AccessLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long getId();

    void setId(Long id);

    @Column(name = "access_level")
    AccessLevelType getAccessLevelType();
    void setAccessLevelType(AccessLevelType accessLevelType);
}