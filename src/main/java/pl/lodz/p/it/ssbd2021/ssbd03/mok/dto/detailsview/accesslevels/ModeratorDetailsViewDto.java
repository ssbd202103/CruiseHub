package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels;

import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccessLevelDetailsViewDto;

@NoArgsConstructor
@ToString
public class ModeratorDetailsViewDto extends AccessLevelDetailsViewDto {
    public ModeratorDetailsViewDto(boolean enabled, long accLevelVersion) {
        super(enabled, AccessLevelType.MODERATOR, accLevelVersion);
    }
}
