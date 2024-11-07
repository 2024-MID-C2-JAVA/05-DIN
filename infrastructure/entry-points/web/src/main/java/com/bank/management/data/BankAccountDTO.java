package com.bank.management.data;

import java.math.BigDecimal;

/**
 * Data Transfer Object for Bank Account.
 */
public class BankAccountDTO {
    private final String number;
    private final BigDecimal amount;

    private BankAccountDTO(Builder builder) {
        this.number = builder.number;
        this.amount = builder.amount;
    }

    public String getNumber() {
        return number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public static class Builder {
        private String number;
        private BigDecimal amount;

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public BankAccountDTO build() {
            return new BankAccountDTO(this);
        }
    }

    @Override
    public String toString() {
        return "BankAccountDTO{" +
                "number='" + number + '\'' +
                ", amount=" + amount +
                '}';
    }
}
