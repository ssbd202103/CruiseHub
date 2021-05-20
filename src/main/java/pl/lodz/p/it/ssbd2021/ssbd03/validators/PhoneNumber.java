package pl.lodz.p.it.ssbd2021.ssbd03.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.REGEX_INVALID_PHONE_NUMBER;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = ValidationRegex.PHONE_NUMBER, message = REGEX_INVALID_PHONE_NUMBER)
public @interface PhoneNumber {
    String message() default REGEX_INVALID_PHONE_NUMBER;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
