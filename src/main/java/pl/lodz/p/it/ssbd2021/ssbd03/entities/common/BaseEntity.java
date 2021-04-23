package pl.lodz.p.it.ssbd2021.ssbd03.entities.common;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;

@MappedSuperclass
public abstract class BaseEntity {
    @Getter
    @NotNull
    @Column(name = "creation_date_time", updatable = false)
    private LocalDateTime creationDateTime;

    @Getter
    @Setter
    @NotNull
    @Column(name = "last_alter_date_time")
    private LocalDateTime lastAlterDateTime;

    @Getter
    @Setter
    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "created_by_id", updatable = false)
    private Account createdBy;

    @Getter
    @Setter
    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "altered_by_id")
    private Account alteredBy;

    @Getter
    @JoinColumn(name = "alter_type_id")
    @OneToOne
    private AlterTypeWrapper alterType;

    @Getter
    @Setter
    @PositiveOrZero
    @Version
    @Column(nullable=false)
    private Long version;

    @PrePersist
    private void prePersist() {
        alterType = new AlterTypeWrapper(AlterType.INSERT);
        creationDateTime = LocalDateTime.now();
        lastAlterDateTime = creationDateTime; //referencing creationDateTime as LDT is immutable
    }
}
