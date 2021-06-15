package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

public class MapperException extends BaseAppException{
    public MapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperException(String message) {
        super(message);
    }
}
