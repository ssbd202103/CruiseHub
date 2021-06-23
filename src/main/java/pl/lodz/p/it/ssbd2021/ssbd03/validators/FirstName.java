package pl.lodz.p.it.ssbd2021.ssbd03.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.REGEX_INVALID_NAME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@NotEmpty(message = CONSTRAINT_NOT_EMPTY)
@Size(max = 64, message = FIRST_NAME_SIZE)
@Pattern(regexp = ValidationRegex.FIRST_SECOND_HARBOR_CRUISEGROUP_NAME, message = REGEX_INVALID_FIRST_NAME)
public @interface FirstName {
    String message() default REGEX_INVALID_FIRST_NAME;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
