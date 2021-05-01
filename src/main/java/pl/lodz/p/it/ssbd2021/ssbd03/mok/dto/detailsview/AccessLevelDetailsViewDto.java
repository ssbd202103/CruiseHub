package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class AccessLevelDetailsViewDto {
    private boolean enabled;

    private AccessLevelType accessLevelType;

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
