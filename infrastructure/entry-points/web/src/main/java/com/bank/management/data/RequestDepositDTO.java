package com.bank.management.data;

import java.math.BigDecimal;

public class RequestDepositDTO {
    private String customerId;
    private String accountNumber;
    private BigDecimal amount;
    private String type;

    private RequestDepositDTO(Builder builder) {
        this.customerId = builder.customerId;
        this.accountNumber = builder.accountNumber;
        this.amount = builder.amount;
        this.type = builder.type;
    }

    private RequestDepositDTO(String customerId, String accountNumber, BigDecimal amount, String type) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.type = type;
    }

    // Getters
    public String getCustomerId() {
        return customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public static class Builder {
        private String customerId;
        private String accountNumber;
        private BigDecimal amount;
        private String type;

        public Builder setCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public RequestDepositDTO build() {
            return new RequestDepositDTO(this);
        }
    }

    @Override
    public String toString() {
        return "RequestDepositDTO{" +
                "customerId=" + customerId +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                '}';
    }
}
