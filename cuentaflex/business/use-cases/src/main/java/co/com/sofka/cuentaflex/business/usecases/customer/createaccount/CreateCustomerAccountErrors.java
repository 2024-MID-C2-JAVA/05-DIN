package co.com.sofka.cuentaflex.business.usecases.customer.createaccount;

import co.com.sofka.shared.business.usecases.Error;

public final class CreateCustomerAccountErrors {
    public static final Error CUSTOMER_NOT_FOUND = new Error(
            "CUSTOMER_NOT_FOUND",
            "Customer not found."
    );

    public static final Error NEGATIVE_INITIAL_AMOUNT = new Error(
            "NEGATIVE_INITIAL_AMOUNT",
            "The initial amount cannot be less than 0."
    );
}
