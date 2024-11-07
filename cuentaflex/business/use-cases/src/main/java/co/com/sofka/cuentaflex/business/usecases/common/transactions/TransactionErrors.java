package co.com.sofka.cuentaflex.business.usecases.common.transactions;

import co.com.sofka.shared.business.usecases.Error;

public class TransactionErrors {
    public static final Error ACCOUNT_NOT_FOUND = new co.com.sofka.shared.business.usecases.Error(
            "ACCOUNT_NOT_FOUND",
            "The account was not found."
    );

    public static final Error INVALID_AMOUNT = new Error(
            "INVALID_AMOUNT",
            "The amount to deposit doesn't reach the minimum value"
    );

    public static final Error INSUFFICIENT_FUNDS = new Error(
            "INSUFFICIENT_FUNDS",
            "Insufficient funds to do the transaction"
    );
}
