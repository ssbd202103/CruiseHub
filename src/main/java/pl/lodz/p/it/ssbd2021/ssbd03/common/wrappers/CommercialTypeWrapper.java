package pl.lodz.p.it.ssbd2021.ssbd03.common.wrappers;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.model.CommercialType;

import javax.persistence.*;

@Entity(name = "commercial_type")
public class CommercialTypeWrapper {
    @Getter
    @Id
    @SequenceGenerator(name = "ACCOUNT_SEQ_GEN", sequenceName = "account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GEN")
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, name = "commercial_type")
    @Setter
    private CommercialType commercialType;

    public CommercialTypeWrapper(Integer id, CommercialType commercialType) {
        this.id = id;
        this.commercialType = commercialType;
    }

    public CommercialTypeWrapper() {
    }
}
