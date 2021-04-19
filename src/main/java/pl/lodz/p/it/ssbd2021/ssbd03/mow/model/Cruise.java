package pl.lodz.p.it.ssbd2021.ssbd03.mow.model;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.asm.Advice;
import pl.lodz.p.it.ssbd2021.ssbd03.common.BaseEntity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity(name = "cruises")
public class Cruise extends BaseEntity {

    @Getter
    @Id
    @SequenceGenerator(name = "ACCOUNT_SEQ_GEN", sequenceName = "account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GEN")
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
    @JoinColumn(name = "cruises_groups_id")
    private CruiseGroup crusesGroup;


}

