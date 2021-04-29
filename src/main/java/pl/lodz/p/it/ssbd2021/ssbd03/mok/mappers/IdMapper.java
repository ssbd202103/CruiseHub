package pl.lodz.p.it.ssbd2021.ssbd03.mok.mappers;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.IdDto;

/**
 * Klasa odpowiedzialna za konwersję IdDto na longa
 */
public class IdMapper {

    /**
     * Metoda konwertująca IdDto na longa
     * @param idDto Obiekt klasy idDto
     * @return ID jako long
     */
    public static long toLong(IdDto idDto) {
        return idDto.getId();
    }
}
