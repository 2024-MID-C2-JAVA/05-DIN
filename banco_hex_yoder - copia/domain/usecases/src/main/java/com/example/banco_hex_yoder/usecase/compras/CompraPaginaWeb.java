package com.example.banco_hex_yoder.usecase.compras;

import com.example.banco_hex_yoder.model.Account;
import com.example.banco_hex_yoder.gateway.AccountGateway;
import java.math.BigDecimal;

public class CompraPaginaWeb {

    private final AccountGateway accountGateway;
    private final BigDecimal costoSeguroCompraWeb;

    public CompraPaginaWeb(AccountGateway accountGateway, BigDecimal costoSeguroCompraWeb) {
        this.accountGateway = accountGateway;
        this.costoSeguroCompraWeb = costoSeguroCompraWeb;
    }

    public Account realizarCompra(Integer cuentaOrigenNumber, BigDecimal monto) {
        Account cuentaOrigen = accountGateway.findByNumber(cuentaOrigenNumber)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta origen no encontrada"));

        BigDecimal montoTotal = monto.add(costoSeguroCompraWeb);

        if (cuentaOrigen.getAmount().compareTo(montoTotal) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar la compra");
        }


        cuentaOrigen.setAmount(cuentaOrigen.getAmount().subtract(montoTotal));


        return accountGateway.save(cuentaOrigen);
    }
}
