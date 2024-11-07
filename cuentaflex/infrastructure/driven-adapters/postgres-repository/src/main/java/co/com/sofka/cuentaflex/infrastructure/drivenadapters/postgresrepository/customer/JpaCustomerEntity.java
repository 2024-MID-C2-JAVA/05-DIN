package co.com.sofka.cuentaflex.infrastructure.drivenadapters.postgresrepository.customer;

import co.com.sofka.cuentaflex.infrastructure.drivenadapters.postgresrepository.account.JpaAccountEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "customer")
@Table(name = "customer")
public class JpaCustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<JpaAccountEntity> accounts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<JpaAccountEntity> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<JpaAccountEntity> accounts) {
        this.accounts = accounts;
    }
}
