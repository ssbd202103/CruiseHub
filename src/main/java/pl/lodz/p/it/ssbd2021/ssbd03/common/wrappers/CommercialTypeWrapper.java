package pl.lodz.p.it.ssbd2021.ssbd03.common.wrappers;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.model.CommercialType;

import javax.persistence.*;

@Entity(name = "commercial_types")
public class CommercialTypeWrapper {
    @Getter
    @Id
    @SequenceGenerator(name = "ACCOUNT_SEQ_GEN", sequenceName = "account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GEN")
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, name = "name")
    @Setter
    private CommercialType name;

    public CommercialTypeWrapper(Integer id, CommercialType commercialType_name) {
        this.id = id;
        this.name = commercialType_name;
    }

    public CommercialTypeWrapper() {
    }
}
