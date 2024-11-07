package co.com.sofka.cuentaflex.infrastructure.drivenadapters.postgresrepository.customer;

import co.com.sofka.cuentaflex.domain.drivenports.repositories.CustomerRepository;
import co.com.sofka.cuentaflex.domain.models.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresCustomerRepository implements CustomerRepository {
    private final JpaCustomerRepository jpaCustomerRepository;

    public PostgresCustomerRepository(JpaCustomerRepository jpaCustomerRepository) {
        this.jpaCustomerRepository = jpaCustomerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        JpaCustomerEntity entity = JpaCustomerMapper.fromModelToJpaCustomer(customer);
        JpaCustomerEntity savedEntity = this.jpaCustomerRepository.save(entity);
        return JpaCustomerMapper.fromJpaToModelCustomer(savedEntity);
    }

    @Override
    public boolean existsCustomer(String customerId) {
        return this.jpaCustomerRepository.existsById(Integer.parseInt(customerId));
    }
}
