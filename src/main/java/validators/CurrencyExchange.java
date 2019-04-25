package validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { CurrencyExchangeValidator.class })
public @interface CurrencyExchange {
    String message()
            default "Transfer not possible because exchange between currencies is not allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
