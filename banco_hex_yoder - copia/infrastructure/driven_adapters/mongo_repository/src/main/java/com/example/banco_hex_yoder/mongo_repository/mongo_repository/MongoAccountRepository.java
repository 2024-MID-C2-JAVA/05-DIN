package com.example.banco_hex_yoder.mongo_repository.mongo_repository;

import com.example.banco_hex_yoder.gateway.AccountGateway;
import com.example.banco_hex_yoder.model.Account;
import com.example.banco_hex_yoder.mongo_repository.data.repositorios.AccountMongoRepository;
import com.example.banco_hex_yoder.mongo_repository.data.repositorios.TransactionAccountDetailMongoRepository;
import com.example.banco_hex_yoder.mongo_repository.data.repositorios.TransactionMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "app.useDB", havingValue = "1")
public class MongoAccountRepository implements AccountGateway {

    private final AccountMongoRepository accountMongoRepository;
    private final TransactionMongoRepository transactionMongoRepository;
    private final TransactionAccountDetailMongoRepository transactionAccountDetailMongoRepository;

    @Autowired
    public MongoAccountRepository(AccountMongoRepository accountMongoRepository,
                                  TransactionMongoRepository transactionMongoRepository,
                                  TransactionAccountDetailMongoRepository transactionAccountDetailMongoRepository) {
        this.accountMongoRepository = accountMongoRepository;
        this.transactionMongoRepository = transactionMongoRepository;
        this.transactionAccountDetailMongoRepository = transactionAccountDetailMongoRepository;
    }

    @Override
    public Optional<Account> findById(Integer id) {
        return accountMongoRepository.findById(id);
    }

    @Override
    public Optional<Account> findByNumber(Integer number) {
        return accountMongoRepository.findByNumber(number);
    }

    @Override
    public void updateBalance(Integer accountId, BigDecimal newBalance) {
        accountMongoRepository.updateBalance(accountId, newBalance);
    }

    @Override
    public void registrarTransaccion(BigDecimal monto, BigDecimal costo, String tipoTransaccion, Integer cuentaOrigenNumber, Integer cuentaDestinoNumber) {
        String transactionId = transactionMongoRepository.saveTransaction(monto, costo, tipoTransaccion);
        transactionAccountDetailMongoRepository.saveTransactionDetail(transactionId, cuentaOrigenNumber, "ordenante");
        transactionAccountDetailMongoRepository.saveTransactionDetail(transactionId, cuentaDestinoNumber, "beneficiario");
    }

    @Override
    public boolean esCuentaDeUsuario(Integer accountNumber, String username) {
        return accountMongoRepository.esCuentaDeUsuario(accountNumber, username);
    }

    @Override
    public Account save(Account account) {
        return accountMongoRepository.save(account);
    }
}
