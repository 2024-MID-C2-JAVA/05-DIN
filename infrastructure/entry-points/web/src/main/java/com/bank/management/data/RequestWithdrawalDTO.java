package com.bank.management.data;

import java.math.BigDecimal;

public class RequestWithdrawalDTO {
    private final String customerId;
    private final String accountNumber;
    private final BigDecimal amount;

    private RequestWithdrawalDTO(Builder builder) {
        this.customerId = builder.customerId;
        this.accountNumber = builder.accountNumber;
        this.amount = builder.amount;
    }

    public RequestWithdrawalDTO(String customerId, String accountNumber, BigDecimal amount) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public static class Builder {
        private String customerId;
        private String accountNumber;
        private BigDecimal amount;

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

        public RequestWithdrawalDTO build() {
            return new RequestWithdrawalDTO(this);
        }
    }
}
