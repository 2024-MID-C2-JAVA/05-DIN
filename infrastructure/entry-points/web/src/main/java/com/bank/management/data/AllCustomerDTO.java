package com.bank.management.data;

/**
 * Data Transfer Object for Customer.
 */
public class AllCustomerDTO {
    private final String username;
    private final String id;

    private AllCustomerDTO(Builder builder) {
        this.username = builder.username;
        this.id = builder.id;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {

        return username;
    }

    public static class Builder {
        private String username;
        private String id;

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }


        public AllCustomerDTO build() {

            return new AllCustomerDTO(this);
        }
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "username='" + username + '\'' +
                "id='" + id + '\'' +
                '}';
    }
}
