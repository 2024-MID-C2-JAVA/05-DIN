package com.bank.management.data;

import java.math.BigDecimal;

public class ResponseDepositDTO {
    private String accountNumber;
    private BigDecimal amount;
    private String message;

    private ResponseDepositDTO(Builder builder) {
        this.accountNumber = builder.accountNumber;
        this.amount = builder.amount;
        this.message = builder.message;
    }

    // Getters


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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Builder {
        private String accountNumber;
        private BigDecimal amount;
        private String message;
        public Builder setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public ResponseDepositDTO build() {
            return new ResponseDepositDTO(this);
        }
    }

    @Override
    public String toString() {
        return "ResponseDepositDTO{" +
                "accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", message='" + message + '\'' +
                '}';
    }
}
