package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;

import lombok.*;

import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class CruisePictureDto {

    private String dataURL;

    private String file;
    @Positive
    private long version;

}
