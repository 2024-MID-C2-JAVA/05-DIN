package co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.endpoints.customer.create;

public final class CreateCustomerResponseDto {
    private String customerId;
    private String username;

    public CreateCustomerResponseDto() {
    }

    public CreateCustomerResponseDto(String customerId, String username) {
        this.customerId = customerId;
        this.username = username;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
