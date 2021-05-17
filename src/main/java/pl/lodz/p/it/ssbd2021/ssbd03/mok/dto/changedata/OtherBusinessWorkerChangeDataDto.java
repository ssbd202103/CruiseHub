package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtherBusinessWorkerChangeDataDto extends AbstractAccountDto {
    @PhoneNumber
    private String newPhoneNumber;
    private Long accVersion;

    public OtherBusinessWorkerChangeDataDto(String login, Long version, String newPhoneNumber,Long accVersion) {
        super(login, version);
        this.newPhoneNumber = newPhoneNumber;
        this.accVersion=accVersion;
    }
}
