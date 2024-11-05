package com.bank.management.data;

public class RequestGetCustomerDTO {
    private String id;

    public RequestGetCustomerDTO() {}

    public RequestGetCustomerDTO(String id) {
        this.id = id;
    }

    // Getter y Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
