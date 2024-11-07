package co.com.sofka.cuentaflex.infrastructure.drivenadapters.mongorepository.customer;

import co.com.sofka.cuentaflex.infrastructure.drivenadapters.mongorepository.account.MongoAccountDocument;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "customers")
public class MongoCustomerDocument {
    @Id
    private String id;
    private String username;
    private LocalDateTime createdAt;
    private boolean isDeleted;
    private List<MongoAccountDocument> accounts;

    public MongoCustomerDocument() {
    }

    public MongoCustomerDocument(String id, String username, LocalDateTime createdAt, boolean isDeleted, List<MongoAccountDocument> accounts) {
        this.id = id;
        this.username = username;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
        this.accounts = accounts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public List<MongoAccountDocument> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<MongoAccountDocument> accounts) {
        this.accounts = accounts;
    }
}
