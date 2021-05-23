package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditAttractionDto implements SignableEntity {

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String name;

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String newDescription;

    @Positive(message = CONSTRAINT_POSITIVE_ERROR)
    private double newPrice;

    @Positive(message = CONSTRAINT_POSITIVE_ERROR)
    private long newNumberOfSeats;

    private boolean newHasFreeSpots;

    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO_ERROR)
    private boolean version;

    @JsonIgnore
    @Override
    public String getSignablePayload() {
        return name + "." + version;
    }
}
