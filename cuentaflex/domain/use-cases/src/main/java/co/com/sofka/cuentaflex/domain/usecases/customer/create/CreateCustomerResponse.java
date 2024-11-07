package co.com.sofka.cuentaflex.domain.usecases.customer.create;

public final class CreateCustomerResponse {
    private final String customerId;
    private final String username;

    public CreateCustomerResponse(String customerId, String username) {
        this.customerId = customerId;
        this.username = username;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getUsername() {
        return username;
    }
}
