package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccessLevelDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.CompanyName;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Setter
public class BusinessWorkerDetailsViewDto extends AccessLevelDetailsViewDto {

    @PhoneNumber
    private String phoneNumber;

    private boolean confirmed;

    @CompanyName
    private String companyName;


    public BusinessWorkerDetailsViewDto(boolean enabled, String phoneNumber, boolean confirmed, String companyName,Long accLevelVersion) {
        super(enabled, AccessLevelType.BUSINESS_WORKER,accLevelVersion);
        this.phoneNumber = phoneNumber;
        this.confirmed = confirmed;
        this.companyName = companyName;
    }
}
