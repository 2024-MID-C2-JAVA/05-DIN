package co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.endpoints.customer.createaccount;

import java.math.BigDecimal;

public final class CreateCustomerAccountRequestDto {
    private String customerId;
    private BigDecimal amount;
    private String initializationVector;
    private String secretKey;

    public CreateCustomerAccountRequestDto() {
    }

    public CreateCustomerAccountRequestDto(String customerId, BigDecimal amount, String initializationVector, String secretKey) {
        this.customerId = customerId;
        this.amount = amount;
        this.initializationVector = initializationVector;
        this.secretKey = secretKey;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
