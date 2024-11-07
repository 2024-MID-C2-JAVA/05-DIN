package co.com.sofka.cuentaflex.business.usecases.customer.createaccount;

import java.math.BigDecimal;

public final class CreateCustomerAccountResponse {
    private final String accountId;
    private final String encryptedNumber;
    private final BigDecimal amount;

    public CreateCustomerAccountResponse(String accountId, String encryptedNumber, BigDecimal amount) {
        this.accountId = accountId;
        this.encryptedNumber = encryptedNumber;
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getEncryptedNumber() {
        return encryptedNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
