package com.example.banco_hex_yoder.usecase.depositos;

import com.example.banco_hex_yoder.model.Account;
import com.example.banco_hex_yoder.gateway.AccountGateway;

import java.math.BigDecimal;

public class DepositoDesdeSucursal {

    private final AccountGateway accountGateway;
    private final BigDecimal costoDepositoSucursal;

    public DepositoDesdeSucursal(AccountGateway accountGateway, BigDecimal costoDepositoSucursal) {
        this.accountGateway = accountGateway;
        this.costoDepositoSucursal = costoDepositoSucursal;
    }

    public Account realizarDeposito(Integer cuentaOrigenNumber, Integer cuentaDestinoNumber, BigDecimal monto) {
        Account cuentaOrigen = accountGateway.findByNumber(cuentaOrigenNumber)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta origen no encontrada"));
        Account cuentaDestino = accountGateway.findByNumber(cuentaDestinoNumber)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta destino no encontrada"));

        if (cuentaOrigen.getAmount().compareTo(monto) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar el depósito");
        }

        BigDecimal montoFinal = monto.subtract(costoDepositoSucursal);
        cuentaOrigen.setAmount(cuentaOrigen.getAmount().subtract(monto));
        cuentaDestino.setCreatedAt(cuentaDestino.getCreatedAt());

        cuentaDestino.setAmount(cuentaDestino.getAmount().add(montoFinal));

        accountGateway.save(cuentaOrigen);
        accountGateway.save(cuentaDestino);


        accountGateway.registrarTransaccion(monto, costoDepositoSucursal, "DepositoSucursal", cuentaOrigenNumber, cuentaDestinoNumber);

        return cuentaDestino;
    }
}
