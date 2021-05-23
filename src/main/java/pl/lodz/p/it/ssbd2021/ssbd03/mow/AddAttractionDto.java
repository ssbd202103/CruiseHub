package pl.lodz.p.it.ssbd2021.ssbd03.mow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE_ERROR;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddAttractionDto {
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String cruiseGroupName;

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String name;

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String description;

    @Positive(message = CONSTRAINT_POSITIVE_ERROR)
    private double price;
}
