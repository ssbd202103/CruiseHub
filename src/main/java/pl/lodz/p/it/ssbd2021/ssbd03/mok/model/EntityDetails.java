package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
//@Embeddable
@MappedSuperclass
public class EntityDetails {
    @Version
    private Long version;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(name = "last_alter_date_time")
    private LocalDateTime lastAlterDateTime;

    @OneToOne
    @JoinColumn(name = "created_by_id")
    private Account createdBy;

    @OneToOne
    @JoinColumn(name = "altered_by_id")
    private Account alteredBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "alter_type")
    private AlterType alterType;
}
