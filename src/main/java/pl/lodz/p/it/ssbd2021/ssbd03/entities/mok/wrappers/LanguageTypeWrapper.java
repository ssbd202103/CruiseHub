package pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.wrappers;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;

@Entity(name = "language_types")
public class LanguageTypeWrapper {
    @Getter
    @Id
    @GeneratedValue
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    @Setter
    private LanguageType name;
    
    public LanguageTypeWrapper(LanguageType name) {
        this.name = name;
    }
    
    public LanguageTypeWrapper() {
        
    }
}