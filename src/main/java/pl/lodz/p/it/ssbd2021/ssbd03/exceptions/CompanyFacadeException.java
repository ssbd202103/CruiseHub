package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

import static pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company.NIP_UNIQUE_CONSTRAINT;

/**
 * Klasa reprezentująca wyjątki fasad firm
 */
public class CompanyFacadeException extends FacadeException {

    public CompanyFacadeException(String message) {
        super(message);
    }

    public CompanyFacadeException(String message, Throwable cause) {
        super(message, cause);
    }

    public static CompanyFacadeException nipNameReserved(Throwable cause) throws CompanyFacadeException {
        throw new CompanyFacadeException(NIP_UNIQUE_CONSTRAINT, cause);
    }

}
