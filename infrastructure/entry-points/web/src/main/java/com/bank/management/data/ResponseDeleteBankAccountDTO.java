package com.bank.management.data;

public class ResponseDeleteBankAccountDTO {
    private final String message;
    private final String accountNumber;

    private ResponseDeleteBankAccountDTO(Builder builder) {
        this.message = builder.message;
        this.accountNumber = builder.accountNumber;
    }

    // Getters
    public String getMessage() {
        return message;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public static class Builder {
        private String message;
        private String accountNumber;

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public ResponseDeleteBankAccountDTO build() {
            return new ResponseDeleteBankAccountDTO(this);
        }
    }

    @Override
    public String toString() {
        return "ResponseDeleteBankAccountDTO{" +
                "message='" + message + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
