package validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { SufficientFundsValidator.class })
public @interface SufficientFunds {
    String message()
            default "Transfer not possible due to insufficient funds";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
