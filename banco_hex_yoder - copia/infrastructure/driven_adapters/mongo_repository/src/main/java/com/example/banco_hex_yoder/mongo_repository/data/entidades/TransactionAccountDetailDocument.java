package com.example.banco_hex_yoder.mongo_repository.data.entidades;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transaction_account_detail")
public class TransactionAccountDetailDocument {

    @Id
    private String id;
    private String transactionId;
    private Integer accountId;
    private String transactionRole;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getTransactionRole() {
        return transactionRole;
    }

    public void setTransactionRole(String transactionRole) {
        this.transactionRole = transactionRole;
    }
}
