package com.bank.management.data;

import java.math.BigDecimal;

/**
 * Data Transfer Object for Bank Account.
 */
public class RequestCreateAccountDTO {

    private String customerId;
    private BigDecimal amount;

    public RequestCreateAccountDTO() {}

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BankAccountDTO{" +
                ", customerId=" + customerId +
                ", amount=" + amount +
                '}';
    }
}
