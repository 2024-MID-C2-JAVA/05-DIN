package com.bank.management.data;

/**
 * Data Transfer Object for Customer.
 */
public class CustomerDTO {
    private final String username;

    private CustomerDTO(Builder builder) {
        this.username = builder.username;
    }

    public String getUsername() {
        return username;
    }

    public static class Builder {
        private String username;

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public CustomerDTO build() {
            return new CustomerDTO(this);
        }
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "username='" + username + '\'' +
                '}';
    }
}
