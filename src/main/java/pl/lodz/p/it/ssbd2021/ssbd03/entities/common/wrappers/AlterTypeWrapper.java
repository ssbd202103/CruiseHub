package pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;

import javax.persistence.*;

@Entity(name = "alter_types")
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
