package com.example.banco_hex_yoder.dtos.depositoDTO;

import java.math.BigDecimal;

public class DepositoResponseDTO {

    private String cuentaDestino;
    private BigDecimal saldoActual;

    public DepositoResponseDTO(String cuentaDestino, BigDecimal saldoActual) {
        this.cuentaDestino = cuentaDestino;
        this.saldoActual = saldoActual;
    }


    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public BigDecimal getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(BigDecimal saldoActual) {
        this.saldoActual = saldoActual;
    }
}
