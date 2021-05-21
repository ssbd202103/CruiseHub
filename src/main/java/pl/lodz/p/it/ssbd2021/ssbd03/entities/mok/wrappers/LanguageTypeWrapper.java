package pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.wrappers;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;

import javax.persistence.*;

import static pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.wrappers.LanguageTypeWrapper.NAME_CONSTRAINT;


@Entity(name = "language_types")
@NamedQueries({
        @NamedQuery(name = "LanguageTypeWrapper.findByName", query = "SELECT lt FROM language_types lt WHERE lt.name = :name")
})
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = NAME_CONSTRAINT)
        }
)
public class LanguageTypeWrapper {
    public static final String NAME_CONSTRAINT = "language_types_name_unique_constraint";
    @Getter
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    @Setter
    @Getter
    private LanguageType name;

    public LanguageTypeWrapper(LanguageType name) {
        this.name = name;
    }

    public LanguageTypeWrapper() {

    }
}