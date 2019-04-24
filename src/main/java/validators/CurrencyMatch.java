package validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { CurrencyMatchValidator.class })
public @interface CurrencyMatch {
    String message()
            default "Transaction not possible due to currency mismatch";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
