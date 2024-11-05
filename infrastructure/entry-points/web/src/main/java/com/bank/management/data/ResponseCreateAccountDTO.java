package com.bank.management.data;

/**
 * Data Transfer Object for Bank Account.
 */
public class ResponseCreateAccountDTO {

    private final String number;
    private final String message;

    private ResponseCreateAccountDTO(Builder builder) {
        this.number = builder.number;
        this.message = builder.message;
    }

    public String getNumber() {
        return number;
    }

    public String getMessage() {
        return message;
    }

    public static class Builder {
        private String number;
        private String message;

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public ResponseCreateAccountDTO build() {
            return new ResponseCreateAccountDTO(this);
        }
    }

    @Override
    public String toString() {
        return "ResponseCreateAccountDTO{" +
                "number=" + number +
                ", message='" + message + '\'' +
                '}';
    }
}
