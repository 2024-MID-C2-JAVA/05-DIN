package com.bank.management.data;

import java.math.BigDecimal;

public class RequestWithdrawalDTO {
    private final String username;
    private final String accountNumber;
    private final BigDecimal amount;

    private RequestWithdrawalDTO(Builder builder) {
        this.username = builder.username;
        this.accountNumber = builder.accountNumber;
        this.amount = builder.amount;
    }

    public RequestWithdrawalDTO(String username, String accountNumber, BigDecimal amount) {
        this.username = username;
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public static class Builder {
        private String username;
        private String accountNumber;
        private BigDecimal amount;

        public Builder setUsername(String username) {
            this.username = username;
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
