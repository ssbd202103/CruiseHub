package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccessLevelDetailsViewDto;


public class ModeratorDetailsViewDto extends AccessLevelDetailsViewDto {
    public ModeratorDetailsViewDto(boolean enabled) {
        super(enabled, AccessLevelType.MODERATOR);
    }
}
