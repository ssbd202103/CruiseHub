package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import java.time.LocalDateTime;

public class EntityDetails {
    private Long id;
    private Long version;

    private LocalDateTime creationDateTime;
    private Account createdBy;

    private LocalDateTime lastAlterDateTime;
    private Account alteredBy;
    private AlterType alterType;
}
