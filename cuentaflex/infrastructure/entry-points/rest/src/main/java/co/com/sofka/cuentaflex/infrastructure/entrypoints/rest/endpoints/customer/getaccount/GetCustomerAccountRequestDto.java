package co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.endpoints.customer.getaccount;

public class GetCustomerAccountRequestDto {
    private String customerId;
    private String accountId;
    private String initializationVector;
    private String secretKey;

    public GetCustomerAccountRequestDto() {
    }

    public GetCustomerAccountRequestDto(String customerId, String accountId, String initializationVector, String secretKey) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.initializationVector = initializationVector;
        this.secretKey = secretKey;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getInitializationVector() {
        return initializationVector;
    }

    public void setInitializationVector(String initializationVector) {
        this.initializationVector = initializationVector;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
