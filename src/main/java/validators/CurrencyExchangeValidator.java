package validators;

import model.Account;
import model.Transfer;
import service.AccountService;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrencyExchangeValidator implements ConstraintValidator<CurrencyExchange, Transfer> {

    @Inject
    private AccountService accountService;

    public void initialize(CurrencyExchange currencyExchange) {
    }

    public boolean isValid(Transfer transfer,
                           ConstraintValidatorContext constraintValidatorContext) {
        Account fromAccount = accountService.getAccount(transfer.getFromAccount());
        Account toAccount = accountService.getAccount(transfer.getToAccount());

        return !(fromAccount.getOwnerId().equals(toAccount.getOwnerId()) &&
                !fromAccount.getCurrency().equals(toAccount.getCurrency()));
    }
}

