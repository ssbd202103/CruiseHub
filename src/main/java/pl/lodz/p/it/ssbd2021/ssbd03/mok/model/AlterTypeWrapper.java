package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "alter_type")
public class AlterTypeWrapper {
    @Getter
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    @Setter
    private AlterType name;

    public AlterTypeWrapper(AlterType name) {
        this.name = name;
    }

    public AlterTypeWrapper() {

    }
}
