package co.com.sofka.cuentaflex.domain.models;

import co.com.sofka.shared.domain.models.BaseAuditableModel;
import co.com.sofka.shared.domain.models.SoftDeletable;

import java.time.LocalDateTime;

public final class Customer extends BaseAuditableModel implements SoftDeletable {
    private String username;
    private boolean isDeleted;

    public Customer(String id, String username, boolean isDeleted) {
        super(id);
        this.username = username;
        this.isDeleted = isDeleted;
    }

    public Customer(String id, String username) {
        super(id);
        this.username = username;
        this.isDeleted = false;
    }

    public Customer(String id, LocalDateTime createdAt, String username, boolean isDeleted) {
        super(id, createdAt);
        this.username = username;
        this.isDeleted = isDeleted;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.isDeleted = deleted;
    }
}
