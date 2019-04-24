package validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { DiffAccountValidator.class })
public @interface DiffAccount {
    String message()
            default "Transfer not possible from account to itself";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
