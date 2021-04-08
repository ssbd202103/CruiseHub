package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Embeddable
public class EntityDetails {
    @Getter
    @NotNull
    @Column(name = "creation_date_time", nullable = false, updatable = false)
    private LocalDateTime creationDateTime;

    @Getter
    @Setter
    @Column(name = "last_alter_date_time")
    private LocalDateTime lastAlterDateTime;

    @Getter
    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "created_by_id", nullable = false, updatable = false)
    private Account createdBy;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "altered_by_id")
    private Account alteredBy;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "alter_type", nullable = false)
    private AlterType alterType;

    @PrePersist
    private void prePersist() {
        creationDateTime = LocalDateTime.now();
    }
}
