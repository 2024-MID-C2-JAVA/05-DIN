package co.com.sofka.cuentaflex.infrastructure.drivenadapters.postgresrepository.customer;


import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCustomerRepository extends JpaRepository<JpaCustomerEntity, Integer> {
}
