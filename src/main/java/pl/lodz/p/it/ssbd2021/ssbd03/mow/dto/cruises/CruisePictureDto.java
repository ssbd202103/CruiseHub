package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises;

import lombok.*;

import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class CruisePictureDto {

    private String dataURL;

    private String pictureName;
    @Positive
    private long version;

}
