package model;

public class Deposit extends Transaction {
    private Long toAccount;

    public Deposit(Long toAccount, Double amount, String currency) {
        super(amount, currency, "Deposit");
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
