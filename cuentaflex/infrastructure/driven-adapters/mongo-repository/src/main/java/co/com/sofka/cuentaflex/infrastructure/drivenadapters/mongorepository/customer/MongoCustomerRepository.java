package co.com.sofka.cuentaflex.infrastructure.drivenadapters.mongorepository.customer;

import co.com.sofka.cuentaflex.business.drivenports.repositories.CustomerRepository;
import co.com.sofka.cuentaflex.business.models.Customer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MongoCustomerRepository implements CustomerRepository {
    private final MongoTemplate mongoTemplate;

    public MongoCustomerRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        MongoCustomerDocument document = MongoCustomerMapper.toDocument(customer);
        return MongoCustomerMapper.toCustomer(mongoTemplate.insert(document));
    }

    @Override
    public boolean existsCustomer(String customerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(customerId));

        return mongoTemplate.exists(query, MongoCustomerDocument.class);
    }
}
