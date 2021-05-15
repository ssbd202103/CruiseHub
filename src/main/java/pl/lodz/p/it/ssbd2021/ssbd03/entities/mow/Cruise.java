package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity(name = "cruises")
public class Cruise extends BaseEntity {

    @Getter
    @Id
    @SequenceGenerator(name = "CRUISE_SEQ_GEN", sequenceName = "cruises_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRUISE_SEQ_GEN")
    @Column(name = "id")
    private Long id;

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

    @Getter
    @Setter
    @NotNull
    @Column(name = "active")
    private boolean active;

    @Getter
    @Setter
    @NotNull
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @NotNull
    @Column(name = "available")
    private Boolean available;

    @Getter
    @Setter
    @NotNull
    @OneToOne
    @JoinColumn(name = "cruises_group_id")
    private CruiseGroup cruisesGroup;

    public Cruise(@NotNull LocalDateTime startDate, @NotNull LocalDateTime endDate, @NotNull boolean active, @NotNull String description, @NotNull Boolean available, @NotNull CruiseGroup cruisesGroup) {
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

