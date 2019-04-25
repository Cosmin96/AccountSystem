package validators;

import model.Deposit;
import model.Transaction;
import model.Transfer;
import model.Withdrawal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ValidTypeValidator implements ConstraintValidator<ValidType, Transaction> {

    public void initialize(ValidType type) {
    }

    public boolean isValid(Transaction transaction,
                           ConstraintValidatorContext constraintValidatorContext) {

       return ((transaction instanceof Withdrawal && transaction.getType().equals("Withdrawal")) ||
            (transaction instanceof Deposit && transaction.getType().equals("Deposit")) ||
               (transaction instanceof Transfer && transaction.getType().equals("Transfer")));
    }
}
