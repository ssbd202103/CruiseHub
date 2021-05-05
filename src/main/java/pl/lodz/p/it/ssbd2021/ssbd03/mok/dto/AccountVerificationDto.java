package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountVerificationDto {

    private String token;

}
