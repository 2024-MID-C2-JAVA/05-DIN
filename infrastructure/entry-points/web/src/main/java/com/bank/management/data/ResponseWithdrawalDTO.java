package com.bank.management.data;

import java.math.BigDecimal;

public class ResponseWithdrawalDTO {
    private String accountNumber;
    private BigDecimal amount;
    private String message;

    private ResponseWithdrawalDTO(Builder builder) {
        this.accountNumber = builder.accountNumber;
        this.amount = builder.amount;
        this.message = builder.message;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getMessage() {
        return message;
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

        public ResponseWithdrawalDTO build() {
            return new ResponseWithdrawalDTO(this);
        }
    }

    @Override
    public String toString() {
        return "ResponseWithdrawalDTO{" +
                "accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", message='" + message + '\'' +
                '}';
    }
}
