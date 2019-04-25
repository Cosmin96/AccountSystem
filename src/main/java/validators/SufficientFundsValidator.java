package validators;

import model.Deposit;
import model.Transaction;
import model.Transfer;
import model.Withdrawal;
import service.AccountService;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SufficientFundsValidator implements ConstraintValidator<SufficientFunds, Transaction> {

    @Inject
    AccountService accountService;

    public void initialize(SufficientFunds sufficientFunds) {
    }

    public boolean isValid(Transaction transaction,
                           ConstraintValidatorContext constraintValidatorContext) {
        return ((transaction instanceof Withdrawal &&
                        accountService.getAccount(((Withdrawal) transaction).getFromAccount()).getBalance()
                                > transaction.getAmount()) ||
                (transaction instanceof Transfer &&
                        accountService.getAccount(((Transfer) transaction).getFromAccount()).getBalance()
                                > transaction.getAmount()) ||
                transaction instanceof Deposit);
    }
}
