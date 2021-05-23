package pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper.NAME_CONSTRAINT;

@Entity(name = "alter_types")
@NamedQueries({
        @NamedQuery(name = "AlterTypeWrapper.findByName", query = "SELECT at FROM alter_types at WHERE at.name = :name")
})
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = NAME_CONSTRAINT),

        }
)
public class AlterTypeWrapper {
    public static final String NAME_CONSTRAINT = "alter_types_name_unique_constraint";
    @Getter
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    @Setter
    @NotNull(message = CONSTRAINT_NOT_NULL)
    private AlterType name;

    public AlterTypeWrapper(AlterType name) {
        this.name = name;
    }

    public AlterTypeWrapper() {

    }
}
