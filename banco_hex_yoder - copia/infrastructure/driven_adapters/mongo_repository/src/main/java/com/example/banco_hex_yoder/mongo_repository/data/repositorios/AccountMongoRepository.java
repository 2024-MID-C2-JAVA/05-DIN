package com.example.banco_hex_yoder.mongo_repository.data.repositorios;

import com.example.banco_hex_yoder.model.Account;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Repository
public class AccountMongoRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public AccountMongoRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<Account> findById(Integer id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Document document = mongoTemplate.findOne(query, Document.class, "account");
        return Optional.ofNullable(toAccount(document));
    }

    public Optional<Account> findByNumber(Integer number) {
        Query query = new Query(Criteria.where("number").is(number));
        Document document = mongoTemplate.findOne(query, Document.class, "account");
        return Optional.ofNullable(toAccount(document));
    }

    public void updateBalance(Integer accountId, BigDecimal newBalance) {
        Query query = new Query(Criteria.where("_id").is(accountId));
        Update update = new Update().set("amount", newBalance);
        mongoTemplate.updateFirst(query, update, "account");
    }

    public boolean esCuentaDeUsuario(Integer accountNumber, String username) {
        Query query = new Query(Criteria.where("username").is(username));
        Document customer = mongoTemplate.findOne(query, Document.class, "customer");

        if (customer == null) return false;

        Integer customerId = customer.getInteger("_id");
        Query accountQuery = new Query(Criteria.where("number").is(accountNumber).and("customer_id").is(customerId));
        Document account = mongoTemplate.findOne(accountQuery, Document.class, "account");

        return account != null;
    }

    public Account save(Account account) {
        Document document = toDocument(account);
        mongoTemplate.save(document, "account");
        return account;
    }

    private Account toAccount(Document document) {
        Account account = new Account();
        account.setId(document.getInteger("_id"));
        account.setNumber(document.getInteger("number"));

        // Convertir el monto a BigDecimal de forma segura
        Object amountObj = document.get("amount");
        if (amountObj instanceof String) {
            account.setAmount(new BigDecimal((String) amountObj));
        } else if (amountObj instanceof Double) {
            account.setAmount(BigDecimal.valueOf((Double) amountObj));
        } else if (amountObj instanceof BigDecimal) {
            account.setAmount((BigDecimal) amountObj);
        }

        account.setCustomerId(document.getInteger("customer_id"));
        account.setCreatedAt(document.getDate("created_at").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        account.setDeleted(document.getBoolean("is_deleted", false));

        return account;
    }

    private Document toDocument(Account account) {
        Document document = new Document();

        // Asigna el _id de la cuenta si existe, de lo contrario omite el campo
        document.put("_id", account.getId());

        // Asigna los demás valores de Account al Document
        document.put("number", account.getNumber());
        document.put("amount", account.getAmount());
        document.put("customer_id", account.getCustomerId());

        // Convertir LocalDate a Date para MongoDB y asignarlo al campo created_at
        document.put("created_at", Date.from(account.getCreatedAt().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        // Asigna el estado de eliminación
        document.put("is_deleted", account.isDeleted());

        return document;
    }
}
