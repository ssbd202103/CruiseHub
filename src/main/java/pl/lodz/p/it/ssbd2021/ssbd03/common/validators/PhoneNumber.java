package pl.lodz.p.it.ssbd2021.ssbd03.common.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = ValidationRegex.PHONE_NUMBER)
public @interface PhoneNumber {
    String message() default "Invalid phone number format"; //should be later replaced with resource bundle

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
