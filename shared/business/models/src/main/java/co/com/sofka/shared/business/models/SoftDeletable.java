package co.com.sofka.shared.business.models;

public interface SoftDeletable {
    boolean isDeleted();
    void setDeleted(boolean deleted);
}
