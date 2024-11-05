package com.bank.management.data;

import java.math.BigDecimal;

public class RequestPurchaseDTO {
    private String accountNumber;
    private BigDecimal amount;
    private String type; // "PHYSICAL" or "ONLINE"

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setPurchaseType(String purchaseType) {
        this.type = purchaseType;
    }
}
