package com.bank.management;

import com.bank.management.data.LogDocument;
import com.bank.management.gateway.LogRepository;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class LogAdapter implements LogRepository {

    private final MongoTemplate mongoTemplate;
    private final JsonMapper jsonMapper;

    public LogAdapter(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.jsonMapper = new JsonMapper();
    }

    @Override
    public void saveLogSucess(String trx) {
        try {
            String transaction = jsonMapper.readValue(trx, String.class);

            Query query = new Query(Criteria.where("_id").exists(true));

            Update update = new Update().push("logsError", transaction);

            mongoTemplate.upsert(query, update, LogDocument.class);

        } catch (Exception e) {
            throw new RuntimeException("Error saving log document", e);
        }
    }
}
