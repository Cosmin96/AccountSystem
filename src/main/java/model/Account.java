package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import exception.CustomException;

import javax.ws.rs.core.Response;

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
        if(id == null) {
            throw new CustomException(Response.Status.FORBIDDEN, "Account id cannot be null");
        }
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBalance() {
        if(balance == null) {
            throw new CustomException(Response.Status.FORBIDDEN, "Balance cannot be null");
        }
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getOwnerId() {
        if(ownerId == null) {
            throw new CustomException(Response.Status.FORBIDDEN, "Owner id cannot be null");
        }
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getCurrency() {
        if(currency == null || currency.equals("")) {
            throw new CustomException(Response.Status.FORBIDDEN, "Currency cannot be null or empty");
        }
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
