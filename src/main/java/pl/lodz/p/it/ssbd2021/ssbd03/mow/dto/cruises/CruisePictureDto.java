package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises;

import lombok.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE_OR_ZERO;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class CruisePictureDto {

    private String dataURL;

    private String pictureName;
    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO)
    private long version;

}
