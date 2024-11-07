package com.example.banco_hex_yoder.mongo_repository.data.entidades;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
public class ClienteDocument {

    @Id
    private Integer id;
    private String username;
    private boolean isDeleted;

    // Getters y Setters


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id.getTimestamp();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
