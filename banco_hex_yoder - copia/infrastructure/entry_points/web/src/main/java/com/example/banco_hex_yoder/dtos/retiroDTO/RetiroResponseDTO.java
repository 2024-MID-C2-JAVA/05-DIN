package com.example.banco_hex_yoder.dtos.retiroDTO;

import java.math.BigDecimal;

public class RetiroResponseDTO {
    private String cuentaOrigen;
    private BigDecimal saldoActual;

    public RetiroResponseDTO() {}

    public RetiroResponseDTO(String cuentaOrigen, BigDecimal saldoActual) {
        this.cuentaOrigen = cuentaOrigen;
        this.saldoActual = saldoActual;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public BigDecimal getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(BigDecimal saldoActual) {
        this.saldoActual = saldoActual;
    }
}
