package co.com.sofka.cuentaflex.infrastructure.drivenadapters.mongorepository.customer;

import co.com.sofka.cuentaflex.business.models.Customer;

public final class MongoCustomerMapper {
    public static MongoCustomerDocument toDocument(Customer customer) {
        return new MongoCustomerDocument(
                customer.getId(),
                customer.getUsername(),
                customer.getCreatedAt(),
                customer.isDeleted(),
                null
        );
    }

    public static Customer toCustomer(MongoCustomerDocument document) {
        return new Customer(
                document.getId(),
                document.getCreatedAt(),
                document.getUsername(),
                document.isDeleted()
        );
    }
}
