package pl.lodz.p.it.ssbd2021.ssbd03.entities.common;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE_OR_ZERO_ERROR;

@MappedSuperclass
public abstract class BaseEntity {
    @Getter
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Column(name = "creation_date_time", updatable = false)
    private LocalDateTime creationDateTime;

    @Getter
    @Setter
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Column(name = "last_alter_date_time")
    private LocalDateTime lastAlterDateTime;

    @Getter
    @Setter
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "created_by_id", updatable = false)
    private Account createdBy;

    @Getter
    @Setter
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "altered_by_id")
    @Valid
    private Account alteredBy;

    @Getter
    @Setter
    @JoinColumn(name = "alter_type_id")
    @OneToOne(cascade = {CascadeType.PERSIST})
    @Valid
    private AlterTypeWrapper alterType;

    @Getter
    @Setter
    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO_ERROR)
    @Version
    @Column(nullable = false)
    private Long version;

    @PrePersist
    private void prePersist() {
        creationDateTime = LocalDateTime.now();
        lastAlterDateTime = creationDateTime; //referencing creationDateTime as LDT is immutable
    }

    @PreUpdate
    public void preUpdate() {
        this.setLastAlterDateTime(LocalDateTime.now());
    }

}
