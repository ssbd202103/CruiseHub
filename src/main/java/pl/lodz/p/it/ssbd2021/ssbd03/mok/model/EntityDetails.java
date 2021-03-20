package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import java.time.LocalDateTime;

public class EntityDetails {
    private Long id;
    private Long version;

    private LocalDateTime creationDateTime;
    private Account createdBy;

    private LocalDateTime lastInCorrectAuthenticationDateTime;
    private String lastInCorrectAuthenticationLogicalAdress;
    private String languageVersion;

    private LocalDateTime lastCorrectAuthenticationDateTime;
    private String lastCorrectAuthenticationLogicalAdress;

    private LocalDateTime lastAlterDateTime;
    private Account alteredBy;
    private AlterType alterType;
}
