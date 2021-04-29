package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@AllArgsConstructor
@Getter
@Setter
public class IdDto {
    @Id
    private long id;
}
