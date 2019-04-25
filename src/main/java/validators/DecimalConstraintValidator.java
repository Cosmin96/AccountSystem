package validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DecimalFormat;

public class DecimalConstraintValidator implements ConstraintValidator<DecimalConstraint, Double> {

    public void initialize(DecimalConstraint decimalConstraint) {
    }

    public boolean isValid(Double amount,
                           ConstraintValidatorContext constraintValidatorContext) {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(100);
        String amountValue = df.format(amount);
        if(!amountValue.contains(".")) {
            return true;
        }
        int integerPlaces = amountValue.indexOf('.');
        int decimalPlaces = amountValue.length() - integerPlaces - 1;
        return decimalPlaces < 3;
    }
}
