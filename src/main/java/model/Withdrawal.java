package model;

public class Withdrawal extends Transaction {
    private Long fromAccount;

    public Withdrawal(Long fromAccount, Double amount, String currency) {
        super(amount, currency, "Withdrawal");
        this.fromAccount = fromAccount;
    }

    public Withdrawal(Long id, Long fromAccount, Double amount, String currency) {
        super(id, amount, currency, "Withdrawal");
        this.fromAccount = fromAccount;
    }

    public Withdrawal() {
    }

    public Long getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Long fromAccount) {
        this.fromAccount = fromAccount;
    }
}
