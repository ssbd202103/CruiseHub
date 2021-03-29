package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Embeddable
public class EntityDetails {
//    @Version
//    private Long version;

    @Column(name = "creation_date_time", nullable = false, updatable = false)
    private LocalDateTime creationDateTime;

    @Column(name = "last_alter_date_time")
    private LocalDateTime lastAlterDateTime;

    @OneToOne
    @JoinColumn(name = "created_by_id", nullable = false, updatable = false)
    private Account createdBy;

    @OneToOne
    @JoinColumn(name = "altered_by_id")
    private Account alteredBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "alter_type", nullable = false)
    private AlterType alterType;

    @PrePersist
    private void prePersist() {
        creationDateTime = LocalDateTime.now();
    }
}
