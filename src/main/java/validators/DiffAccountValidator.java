package validators;

import model.Transfer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DiffAccountValidator implements ConstraintValidator<DiffAccount, Transfer> {

    public void initialize(DiffAccount diffAccount) {
    }

    public boolean isValid(Transfer transfer,
                           ConstraintValidatorContext constraintValidatorContext) {
        return !transfer.getFromAccount().equals(transfer.getToAccount());
    }
}
