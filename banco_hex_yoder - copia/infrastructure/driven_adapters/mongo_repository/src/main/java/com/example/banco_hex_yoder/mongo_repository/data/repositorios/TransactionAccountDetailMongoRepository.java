package com.example.banco_hex_yoder.mongo_repository.data.repositorios;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionAccountDetailMongoRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public TransactionAccountDetailMongoRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void saveTransactionDetail(String transactionId, Integer accountId, String transactionRole) {
        Document detalle = new Document();
        detalle.put("transactionId", transactionId);
        detalle.put("accountId", accountId);
        detalle.put("transactionRole", transactionRole);
        mongoTemplate.insert(detalle, "transaction_account_detail");
    }
}
