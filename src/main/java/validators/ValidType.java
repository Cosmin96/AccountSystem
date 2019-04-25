package validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidTypeValidator.class })
public @interface ValidType {
    String message()
            default "Transaction type is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
