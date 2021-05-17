package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccessLevelDetailsViewDto;


public class AdministratorDetailsViewDto extends AccessLevelDetailsViewDto {
    public AdministratorDetailsViewDto(boolean enabled,Long accLevelVersion) {
        super(enabled, AccessLevelType.ADMINISTRATOR, accLevelVersion);
    }

    public AdministratorDetailsViewDto() {
    }
}
