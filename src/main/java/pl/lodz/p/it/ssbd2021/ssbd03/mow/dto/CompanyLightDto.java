package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanyLightDto {
    private String name;
    private Long nip;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyLightDto that = (CompanyLightDto) o;
        return name.equals(that.name) && nip.equals(that.nip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nip);
    }
}
