package model;

import exception.CustomException;

import javax.ws.rs.core.Response;

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
        if(id == null) {
            throw new CustomException(Response.Status.FORBIDDEN, "Transaction id cannot be null");
        }
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        if(amount == null) {
            throw new CustomException(Response.Status.FORBIDDEN, "Account amount cannot be null");
        }
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        if(currency == null && currency.equals("")) {
            throw new CustomException(Response.Status.FORBIDDEN, "Account currency cannot be null or empty");
        }
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getType() {
        if(type == null) {
            throw new CustomException(Response.Status.FORBIDDEN, "Transaction type cannot be null");
        }
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
