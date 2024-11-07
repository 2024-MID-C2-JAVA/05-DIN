package com.example.banco_hex_yoder.dtos.compraDTO;

import java.math.BigDecimal;

public class CompraResponseDTO {
    private String cuentaOrigenEncriptada;
    private BigDecimal saldoActualizado;

    public CompraResponseDTO() {}

    public CompraResponseDTO(String cuentaOrigenEncriptada, BigDecimal saldoActualizado) {
        this.cuentaOrigenEncriptada = cuentaOrigenEncriptada;
        this.saldoActualizado = saldoActualizado;
    }

    public String getCuentaOrigenEncriptada() {
        return cuentaOrigenEncriptada;
    }

    public void setCuentaOrigenEncriptada(String cuentaOrigenEncriptada) {
        this.cuentaOrigenEncriptada = cuentaOrigenEncriptada;
    }

    public BigDecimal getSaldoActualizado() {
        return saldoActualizado;
    }

    public void setSaldoActualizado(BigDecimal saldoActualizado) {
        this.saldoActualizado = saldoActualizado;
    }
}
