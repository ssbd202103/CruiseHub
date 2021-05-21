package pl.lodz.p.it.ssbd2021.ssbd03.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@NotEmpty(message = CONSTRAINT_NOT_EMPTY)
@Pattern(regexp = ValidationRegex.COMPANY_NAME, message = REGEX_INVALID_COMPANY_NAME)
public @interface CompanyName {
    String message() default REGEX_INVALID_COMPANY_NAME;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
