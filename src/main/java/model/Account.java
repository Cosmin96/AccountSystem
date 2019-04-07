package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    private Long id;
    private Double balance;
    private Long ownerId;
    private String currency;

    public Account() {}

    public Account(Long id, Double balance, Long ownerId, String currency) {
        this.id = id;
        this.balance = balance;
        this.ownerId = ownerId;
        this.currency = currency;
    }

    public Account(Double balance, Long ownerId, String currency) {
        this.balance = balance;
        this.ownerId = ownerId;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", ownerId=" + ownerId +
                ", currency='" + currency + '\'' +
                '}';
    }
}
