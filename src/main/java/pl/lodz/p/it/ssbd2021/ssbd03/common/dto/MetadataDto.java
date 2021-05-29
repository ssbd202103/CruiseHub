package pl.lodz.p.it.ssbd2021.ssbd03.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MetadataDto {

    private LocalDateTime creationDateTime;

    private LocalDateTime lastAlterDateTime;

    private String createdBy;

    private String alteredBy;

    private AlterType alterType;

    private long version;
}
