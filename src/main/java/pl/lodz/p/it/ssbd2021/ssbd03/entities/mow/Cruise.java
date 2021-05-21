package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;

@Entity(name = "cruises")
public class Cruise extends BaseEntity {

    @Getter
    @Id
    @SequenceGenerator(name = "CRUISE_SEQ_GEN", sequenceName = "cruises_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRUISE_SEQ_GEN")
    @Column(name = "id")
    private long id;

    @Getter
    @Setter
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Getter
    @Setter
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Getter
    @Setter
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Column(name = "active")
    private boolean active;

    @Getter
    @Setter
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Column(name = "available")
    private boolean available;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "cruises_group_id")
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    private CruiseGroup cruisesGroup;

    public Cruise(LocalDateTime startDate, LocalDateTime endDate, boolean active,
                  String description, boolean available, CruiseGroup cruisesGroup) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
        this.description = description;
        this.available = available;
        this.cruisesGroup = cruisesGroup;
    }

    public Cruise() {
    }
}

