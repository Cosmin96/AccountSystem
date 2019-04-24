package model;

import validators.CurrencyExchange;
import validators.DiffAccount;

import javax.validation.constraints.NotNull;

@DiffAccount
@CurrencyExchange
public class Transfer extends Transaction {
    @NotNull(message = "Withdrawal account cannot be null")
    private Long fromAccount;

    @NotNull(message = "Deposit account cannot be null")
    private Long toAccount;

    public Transfer(Long fromAccount, Long toAccount, Double amount, String currency) {
        super(amount, currency, "Transfer");
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public Transfer(Long id, Long fromAccount, Long toAccount, Double amount, String currency) {
        super(id, amount, currency, "Transfer");
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public Transfer() {}

    public Long getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Long fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Long getToAccount() {
        return toAccount;
    }

    public void setToAccount(Long toAccount) {
        this.toAccount = toAccount;
    }
}
