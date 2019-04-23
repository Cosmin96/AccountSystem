package model;

import exception.CustomException;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

public class Deposit extends Transaction {
    @NotNull(message = "Deposit account cannot be null")
    private Long toAccount;

    public Deposit(Long toAccount, Double amount, String currency) {
        super(amount, currency, "Deposit");
        this.toAccount = toAccount;
    }

    public Deposit(Long id, Long toAccount, Double amount, String currency) {
        super(id, amount, currency, "Deposit");
        this.toAccount = toAccount;
    }

    public Deposit() {}

    public Long getToAccount() {
        return toAccount;
    }

    public void setToAccount(Long toAccount) {
        this.toAccount = toAccount;
    }
}
