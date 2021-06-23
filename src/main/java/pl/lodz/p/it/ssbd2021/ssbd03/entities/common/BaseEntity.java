package pl.lodz.p.it.ssbd2021.ssbd03.entities.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.it.ssbd2021.ssbd03.common.IdentifiableEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE_OR_ZERO;

@MappedSuperclass
@ToString
public abstract class BaseEntity implements IdentifiableEntity {
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
    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "created_by_id", updatable = false)
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    @ToString.Exclude
    private Account createdBy;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "altered_by_id")
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    @ToString.Exclude
    private Account alteredBy;

    @Getter
    @Setter
    @JoinColumn(name = "alter_type_id")
    @OneToOne(cascade = {CascadeType.PERSIST})
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    @ToString.Exclude
    private AlterTypeWrapper alterType;

    @Getter
    @Setter
    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO)
    @Version
    @Column(nullable = false)
    private long version;

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
