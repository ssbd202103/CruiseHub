package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.wrappers.CommercialTypeWrapper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity(name = "commercials")
public class Commercial extends BaseEntity {
    @Getter
    @Id
    @SequenceGenerator(name = "COMMERCIAL_SEQ_GEN", sequenceName = "commercials_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMERCIAL_SEQ_GEN")
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @NotNull
    @OneToOne
    @JoinColumn(name = "cruises_group_id")
    private CruiseGroup cruisesGroup;

    @Getter
    @Setter
    @JoinColumn(name = "commercial_type_id")
    @OneToOne
    private CommercialTypeWrapper commercialType;


    @Getter
    @Setter
    @NotNull
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Getter
    @Setter
    @NotNull
    @Column(name = "end_date")
    private LocalDateTime endDate;

    public Commercial(Long id, @NotNull CruiseGroup cruisesGroup, CommercialTypeWrapper commercialType, @NotNull LocalDateTime startDate, @NotNull LocalDateTime endDate) {
        this.id = id;
        this.cruisesGroup = cruisesGroup;
        this.commercialType = commercialType;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public Commercial() {
    }
}
