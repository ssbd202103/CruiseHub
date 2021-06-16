package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;

@Getter
@Setter
@NoArgsConstructor
public class CruiseDto extends DeactivateCruiseDto {
    @NotNull(message = CONSTRAINT_NOT_NULL)
    private LocalDateTime startDate;

    @NotNull(message = CONSTRAINT_NOT_NULL)
    private LocalDateTime endDate;

    private boolean active;

    private boolean available;

    @NotNull(message = CONSTRAINT_NOT_NULL)
    private CruiseGroupWithUUIDDto cruiseGroupDto;

    public CruiseDto(String uuid, Long version,
                     LocalDateTime startDate, LocalDateTime endDate,
                     boolean active, boolean available, CruiseGroupWithUUIDDto cruiseGroupDto) {
        super(uuid, version);

        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
        this.available = available;
        this.cruiseGroupDto = cruiseGroupDto;
    }
}
