package com.example.banco_hex_yoder.usecase.depositos;

import com.example.banco_hex_yoder.model.Account;
import com.example.banco_hex_yoder.gateway.AccountGateway;
import java.math.BigDecimal;

public class DepositoDesdeOtraCuenta {

    private final AccountGateway accountGateway;
    private final BigDecimal costoDepositoOtraCuenta;

    public DepositoDesdeOtraCuenta(AccountGateway accountGateway, BigDecimal costoDepositoOtraCuenta) {
        this.accountGateway = accountGateway;
        this.costoDepositoOtraCuenta = costoDepositoOtraCuenta;
    }

    public Account realizarDeposito(Integer cuentaOrigenNumber, Integer cuentaDestinoNumber, BigDecimal monto) {
        Account cuentaOrigen = accountGateway.findByNumber(cuentaOrigenNumber)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta origen no encontrada"));
        Account cuentaDestino = accountGateway.findByNumber(cuentaDestinoNumber)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta destino no encontrada"));

        if (cuentaOrigen.getAmount().compareTo(monto) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar el depósito");
        }

        BigDecimal montoFinal = monto.subtract(costoDepositoOtraCuenta);
        cuentaOrigen.setAmount(cuentaOrigen.getAmount().subtract(monto));
        cuentaDestino.setAmount(cuentaDestino.getAmount().add(montoFinal));

        accountGateway.save(cuentaOrigen);
        accountGateway.save(cuentaDestino);


        accountGateway.registrarTransaccion(monto, costoDepositoOtraCuenta, "DepositoOtraCuenta", cuentaOrigenNumber, cuentaDestinoNumber);

        return cuentaDestino;
    }
}
