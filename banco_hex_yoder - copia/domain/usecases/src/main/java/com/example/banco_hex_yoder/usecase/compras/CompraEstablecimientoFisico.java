package com.example.banco_hex_yoder.usecase.compras;

import com.example.banco_hex_yoder.model.Account;
import com.example.banco_hex_yoder.gateway.AccountGateway;
import java.math.BigDecimal;

public class CompraEstablecimientoFisico {

    private final AccountGateway accountGateway;
    private final BigDecimal costoCompraEstablecimiento;

    public CompraEstablecimientoFisico(AccountGateway accountGateway, BigDecimal costoCompraEstablecimiento) {
        this.accountGateway = accountGateway;
        this.costoCompraEstablecimiento = costoCompraEstablecimiento;
    }

    public Account realizarCompra(Integer cuentaOrigenNumber, BigDecimal monto) {
        Account cuentaOrigen = accountGateway.findByNumber(cuentaOrigenNumber)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta origen no encontrada"));

        BigDecimal montoTotal = monto.add(costoCompraEstablecimiento);

        if (cuentaOrigen.getAmount().compareTo(montoTotal) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar la compra");
        }

        cuentaOrigen.setAmount(cuentaOrigen.getAmount().subtract(montoTotal));
        accountGateway.save(cuentaOrigen);


        accountGateway.registrarTransaccion(montoTotal, costoCompraEstablecimiento, "CompraEstablecimientoFisico", cuentaOrigenNumber, cuentaOrigenNumber);

        return cuentaOrigen;
    }
}
