package pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.wrappers;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;


@Entity(name = "language_types")
@NamedQueries({
        @NamedQuery(name = "LanguageTypeWrapper.findByName", query = "SELECT lt FROM language_types lt WHERE lt.name = :name")
})
public class LanguageTypeWrapper {
    @Getter
    @Id
    @GeneratedValue
    private Long id;


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