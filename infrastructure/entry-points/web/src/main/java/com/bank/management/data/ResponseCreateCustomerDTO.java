package com.bank.management.data;

/**
 * Data Transfer Object for Bank Account.
 */
public class ResponseCreateCustomerDTO {

    private final String username;
    private final String message;

    private ResponseCreateCustomerDTO(Builder builder) {
        this.username = builder.username;
        this.message = builder.message;
    }

    public String getusername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public static class Builder {
        private String username;
        private String message;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public ResponseCreateCustomerDTO build() {
            return new ResponseCreateCustomerDTO(this);
        }
    }

    @Override
    public String toString() {
        return "ResponseCreateAccountDTO{" +
                "username=" + username +
                ", message='" + message + '\'' +
                '}';
    }
}
