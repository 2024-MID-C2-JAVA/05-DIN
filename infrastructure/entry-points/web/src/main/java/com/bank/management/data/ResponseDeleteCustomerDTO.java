package com.bank.management.data;

public class ResponseDeleteCustomerDTO {
    private final String message;
    private final String username;

    private ResponseDeleteCustomerDTO(Builder builder) {
        this.message = builder.message;
        this.username = builder.username;
    }

    // Getters
    public String getMessage() {
        return message;
    }

    public String getusername() {
        return username;
    }

    public static class Builder {
        private String message;
        private String username;

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public ResponseDeleteCustomerDTO build() {
            return new ResponseDeleteCustomerDTO(this);
        }
    }

    @Override
    public String toString() {
        return "ResponseDeleteBankAccountDTO{" +
                "message='" + message + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
