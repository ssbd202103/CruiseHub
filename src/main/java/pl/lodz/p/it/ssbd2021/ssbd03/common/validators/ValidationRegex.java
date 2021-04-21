package pl.lodz.p.it.ssbd2021.ssbd03.common.validators;

public class ValidationRegex {
    public static final String PHONE_NUMBER = "\\+?(?:[\\s\\-/\\\\]?\\d){6,15}";
    public static final String LOGIN = "\\w{3,30}";
    public static final String NAME = "^[\\p{L}'-]+\\.?$";
    public static final String COUNTRY = "^(?:[\\p{L}']+[- ]?)+$";
    public static final String CITY = "^(?:[\\p{L}']+[- ]?)+$";
    public static final String STREET = "^(?:[\\p{L}'.]+[- ]?)+$";
    public static final String POSTCODE = "[a-zA-Z\\d -]{4,10}";
}
