package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.wrappers.LanguageTypeWrapper;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AccountMetadataDto {
    private LocalDateTime creationDateTime;

    private LocalDateTime lastAlterDateTime;

    private String createdBy;

    private String alteredBy;

    private AlterType alterType;

    private long version;

    private LocalDateTime lastIncorrectAuthenticationDateTime;

    private String lastIncorrectAuthenticationLogicalAddress;

    private LocalDateTime lastCorrectAuthenticationDateTime;

    private String lastCorrectAuthenticationLogicalAddress;

    private int numberOfAuthenticationFailures;

    private String languageType;
}
