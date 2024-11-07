package com.example.banco_hex_yoder.usecase.retiros;

import com.example.banco_hex_yoder.model.Account;
import com.example.banco_hex_yoder.gateway.AccountGateway;
import java.math.BigDecimal;

public class RetiroEnCajero {

    private final AccountGateway accountGateway;
    private final BigDecimal costoRetiro;

    public RetiroEnCajero(AccountGateway accountGateway, BigDecimal costoRetiro) {
        this.accountGateway = accountGateway;
        this.costoRetiro = costoRetiro;
    }

    public Account realizarRetiro(Integer cuentaOrigenNumber, Integer cuentaDestinoNumber, BigDecimal monto) {
        Account cuentaOrigen = accountGateway.findByNumber(cuentaOrigenNumber)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta origen no encontrada"));

        BigDecimal montoTotal = monto.add(costoRetiro);

        if (cuentaOrigen.getAmount().compareTo(montoTotal) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar el retiro");
        }

        cuentaOrigen.setAmount(cuentaOrigen.getAmount().subtract(montoTotal));


        accountGateway.save(cuentaOrigen);


        accountGateway.registrarTransaccion(monto, costoRetiro, "RetiroCajero", cuentaOrigenNumber, cuentaDestinoNumber);

        accountGateway.registrarTransaccion(monto, costoRetiro, "DepositoSucursal", cuentaOrigenNumber, cuentaDestinoNumber);


        return cuentaOrigen;
    }
}
