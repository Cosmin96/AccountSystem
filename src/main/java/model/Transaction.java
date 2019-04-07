package model;

public abstract class Transaction {
    private Long id;
    private Double amount;
    private String currency;
    private String type;

    public Transaction() {
    }

    public Transaction(Double amount, String currency, String type) {
        this.amount = amount;
        this.currency = currency;
        this.type = type;
    }

    public Transaction(Long id, Double amount, String currency, String type) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
