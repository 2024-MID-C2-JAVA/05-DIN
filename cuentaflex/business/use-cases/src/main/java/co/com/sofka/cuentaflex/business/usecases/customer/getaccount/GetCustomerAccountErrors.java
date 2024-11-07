package co.com.sofka.cuentaflex.business.usecases.customer.getaccount;

import co.com.sofka.shared.business.usecases.Error;

public final class GetCustomerAccountErrors {
    public static final Error ACCOUNT_NOT_FOUND = new Error(
            "ACCOUNT_NOT_FOUND",
            "The account was not found"
    );
}
