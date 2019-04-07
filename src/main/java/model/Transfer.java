package model;

public class Transfer extends Transaction {
    private Long fromAccount;
    private Long toAccount;

    public Transfer(Long fromAccount, Long toAccount, Double amount, String currency) {
        super(amount, currency, "Transfer");
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
