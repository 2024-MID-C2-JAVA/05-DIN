package co.com.sofka.cuentaflex.domain.drivenports.repositories;

import co.com.sofka.cuentaflex.domain.models.Customer;

public interface CustomerRepository {
    Customer createCustomer(Customer customer);
    boolean existsCustomer(String customerId);
}
