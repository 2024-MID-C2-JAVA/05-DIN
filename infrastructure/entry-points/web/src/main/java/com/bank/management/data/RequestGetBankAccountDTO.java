package com.bank.management.data;

public class RequestGetBankAccountDTO {
    private String id;

    public RequestGetBankAccountDTO() {}

    public RequestGetBankAccountDTO(String id) {
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
