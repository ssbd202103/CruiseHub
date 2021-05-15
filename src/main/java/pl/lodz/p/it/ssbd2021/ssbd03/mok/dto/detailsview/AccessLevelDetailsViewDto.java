package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels.AdministratorDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels.BusinessWorkerDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels.ClientDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels.ModeratorDetailsViewDto;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @Type(value = ClientDetailsViewDto.class, name = "client"),
        @Type(value = BusinessWorkerDetailsViewDto.class, name = "businessWorker"),
        @Type(value = ModeratorDetailsViewDto.class, name = "moderator"),
        @Type(value = AdministratorDetailsViewDto.class, name = "administrator")
})
public abstract class AccessLevelDetailsViewDto {
    private boolean enabled;

    @NotNull
    private AccessLevelType accessLevelType;

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
