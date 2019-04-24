package model;

import validators.CurrencyMatch;
import validators.DecimalConstraint;
import validators.SufficientFunds;
import validators.ValidType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ValidType
@CurrencyMatch
@SufficientFunds
public abstract class Transaction {

    private Long id;

    @Min(value = 0, message = "Transaction amount cannot be negative")
    @NotNull(message = "Transaction amount cannot be null")
    @DecimalConstraint
    private Double amount;

    @NotNull(message = "Transaction currency cannot be null")
    @NotBlank(message = "Transaction currency cannot be empty")
    private String currency;

    @NotNull(message = "Transaction type cannot be null")
    @NotBlank(message = "Transaction type cannot be empty")
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
