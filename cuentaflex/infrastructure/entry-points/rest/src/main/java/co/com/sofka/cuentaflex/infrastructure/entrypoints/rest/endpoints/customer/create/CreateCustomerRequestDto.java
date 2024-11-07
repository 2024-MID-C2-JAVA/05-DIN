package co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.endpoints.customer.create;

public final class CreateCustomerRequestDto {
    private String username;

    public CreateCustomerRequestDto() {
        this.username = null;
    }

    public CreateCustomerRequestDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
