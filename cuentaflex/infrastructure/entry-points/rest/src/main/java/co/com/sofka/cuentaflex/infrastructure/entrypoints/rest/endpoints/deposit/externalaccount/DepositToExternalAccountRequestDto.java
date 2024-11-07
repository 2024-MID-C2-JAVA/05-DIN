package co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.endpoints.deposit.externalaccount;

import java.math.BigDecimal;

public final class DepositToExternalAccountRequestDto {
    private String customerId;
    private String accountId;
    private BigDecimal amount;
    private String encryptedAccountNumberToDeposit;
    private String initializationVector;
    private String secretKey;

    public DepositToExternalAccountRequestDto() {
    }

    public DepositToExternalAccountRequestDto(
            String customerId,
            String accountId,
            BigDecimal amount,
            String encryptedAccountNumberToDeposit,
            String initializationVector,
            String secretKey
    ) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.amount = amount;
        this.encryptedAccountNumberToDeposit = encryptedAccountNumberToDeposit;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getEncryptedAccountNumberToDeposit() {
        return encryptedAccountNumberToDeposit;
    }

    public void setEncryptedAccountNumberToDeposit(String encryptedAccountNumberToDeposit) {
        this.encryptedAccountNumberToDeposit = encryptedAccountNumberToDeposit;
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
