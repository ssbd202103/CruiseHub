package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

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
    @Setter
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "alter_type")
    private AlterType alterType;

    @Getter
    @Setter
    @PositiveOrZero
    @Version
    @Column(nullable=false)
    private Long version;

    @PrePersist
    private void prePersist() {
        alterType = AlterType.CREATE;
        creationDateTime = LocalDateTime.now();
        lastAlterDateTime = creationDateTime; //referencing creationDateTime as LDT is immutable
    }
}
