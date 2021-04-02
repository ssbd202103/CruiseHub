package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "access_level", discriminatorType = DiscriminatorType.STRING)
@TableGenerator(name="AccessLevelIdGenerator", table="generator", pkColumnName="generator_key", valueColumnName="generator_value", pkColumnValue="AccessLevelId")
@Entity(name = "access_levels")
public abstract class AccessLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="AccessLevelIdGenerator")
    @Column(name = "id")
    private Long id;

    private boolean active;

    public abstract AccessLevelType getAccessLevelType();
}