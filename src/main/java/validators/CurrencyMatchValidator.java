package validators;

import model.Deposit;
import model.Transaction;
import model.Transfer;
import model.Withdrawal;
import service.AccountService;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrencyMatchValidator implements ConstraintValidator<CurrencyMatch, Transaction> {

    @Inject
    private AccountService accountService;

    public void initialize(CurrencyMatch currencyMatch) {
    }

    public boolean isValid(Transaction transaction,
                           ConstraintValidatorContext constraintValidatorContext) {
        return ((transaction instanceof Withdrawal &&
                    transaction.getCurrency().equals(
                        accountService.getAccount(((Withdrawal) transaction).getFromAccount()).getCurrency())) ||
                (transaction instanceof Transfer &&
                    transaction.getCurrency().equals(
                        accountService.getAccount(((Transfer) transaction).getFromAccount()).getCurrency())) ||
                transaction instanceof Deposit);
    }
}
