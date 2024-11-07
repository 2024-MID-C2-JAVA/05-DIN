package co.com.sofka.cuentaflex.business.drivenports.repositories;

import co.com.sofka.cuentaflex.business.models.Customer;

public interface CustomerRepository {
    Customer createCustomer(Customer customer);
    boolean existsCustomer(String customerId);
}
