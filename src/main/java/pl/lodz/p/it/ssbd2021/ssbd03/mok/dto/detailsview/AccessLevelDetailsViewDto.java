package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels.AdministratorDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels.BusinessWorkerDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels.ClientDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels.ModeratorDetailsViewDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @Type(value = ClientDetailsViewDto.class, name = "client"),
        @Type(value = BusinessWorkerDetailsViewDto.class, name = "businessWorker"),
        @Type(value = ModeratorDetailsViewDto.class, name = "moderator"),
        @Type(value = AdministratorDetailsViewDto.class, name = "administrator")
})
public abstract class AccessLevelDetailsViewDto {
    private boolean enabled;

    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    private AccessLevelType accessLevelType;

    @PositiveOrZero(message = CONSTRAINT_POSITIVE)
    private long accVersion;

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
