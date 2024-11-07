package com.example.banco_hex_yoder.handlers.compras;

import com.example.banco_hex_yoder.din.request.DinRequest;
import com.example.banco_hex_yoder.din.response.DinResponse;
import com.example.banco_hex_yoder.din.generic.DinError;
import com.example.banco_hex_yoder.dtos.compraDTO.CompraRequestDTO;
import com.example.banco_hex_yoder.dtos.compraDTO.CompraResponseDTO;
import com.example.banco_hex_yoder.encriptacion.EncripcionService;
import com.example.banco_hex_yoder.common.exceptions.AccountNotFoundException;
import com.example.banco_hex_yoder.common.exceptions.EncryptionException;
import com.example.banco_hex_yoder.gateway.AccountGateway;
import com.example.banco_hex_yoder.model.Account;
import com.example.banco_hex_yoder.usecase.compras.CompraEstablecimientoFisico;
import com.example.banco_hex_yoder.usecase.compras.CompraPaginaWeb;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CompraHandler {

    private final CompraEstablecimientoFisico compraFisico;
    private final CompraPaginaWeb compraWeb;
    private final AccountGateway accountGateway;
    private final EncripcionService encripcionService;

    public CompraHandler(CompraEstablecimientoFisico compraFisico, CompraPaginaWeb compraWeb,
                         AccountGateway accountGateway, EncripcionService encripcionService) {
        this.compraFisico = compraFisico;
        this.compraWeb = compraWeb;
        this.accountGateway = accountGateway;
        this.encripcionService = encripcionService;
    }

    public DinResponse<CompraResponseDTO> ejecutarCompraFisico(DinRequest<CompraRequestDTO> request) {
        DinResponse<CompraResponseDTO> response = new DinResponse<>();
        response.setDinHeader(request.getDinHeader());

        try {
            String symmetricKey = request.getDinHeader().getLlaveSimetrica();
            String initializationVector = request.getDinHeader().getVectorInicializacion();
            String username = encripcionService.desencriptar(request.getDinBody().getCustomer(), symmetricKey, initializationVector);

            Integer cuentaOrigenNumber = Integer.parseInt(
                    encripcionService.desencriptar(request.getDinBody().getCuentaOrigen(), symmetricKey, initializationVector)
            );

            Account cuentaOrigen = accountGateway.findByNumber(cuentaOrigenNumber)
                    .orElseThrow(() -> new AccountNotFoundException("Cuenta origen no encontrada"));

            if (!accountGateway.esCuentaDeUsuario(cuentaOrigenNumber, username)) {
                response.setDinError(new DinError("WARNING", "1007", "El usuario no es dueño de la cuenta origen", "Usuario no autorizado"));
                return response;
            }

            BigDecimal monto = request.getDinBody().getMonto();
            Account cuentaActualizada = compraFisico.realizarCompra(cuentaOrigenNumber, monto);

            CompraResponseDTO responseBody = new CompraResponseDTO(
                    encripcionService.encriptar(String.valueOf(cuentaActualizada.getNumber()), symmetricKey, initializationVector),
                    cuentaActualizada.getAmount()
            );
            response.setDinBody(responseBody);

            DinError dinError = new DinError("N", "0000", "Compra realizada exitosamente", "La compra ha sido procesada.");
            response.setDinError(dinError);

        } catch (EncryptionException e) {
            response.setDinError(new DinError("ERROR", "1001", "Error al desencriptar", e.getMessage()));
        } catch (AccountNotFoundException e) {
            response.setDinError(new DinError("WARNING", "1002", "La cuenta no existe", e.getMessage()));
        } catch (Exception e) {
            response.setDinError(new DinError("ERROR", "1006", "Error desconocido", e.getMessage()));
        }

        return response;
    }

    public DinResponse<CompraResponseDTO> ejecutarCompraWeb(DinRequest<CompraRequestDTO> request) {
        DinResponse<CompraResponseDTO> response = new DinResponse<>();
        response.setDinHeader(request.getDinHeader());

        try {
            String symmetricKey = request.getDinHeader().getLlaveSimetrica();
            String initializationVector = request.getDinHeader().getVectorInicializacion();
            String username = encripcionService.desencriptar(request.getDinBody().getCustomer(), symmetricKey, initializationVector);

            Integer cuentaOrigenNumber = Integer.parseInt(
                    encripcionService.desencriptar(request.getDinBody().getCuentaOrigen(), symmetricKey, initializationVector)
            );

            Account cuentaOrigen = accountGateway.findByNumber(cuentaOrigenNumber)
                    .orElseThrow(() -> new AccountNotFoundException("Cuenta origen no encontrada"));

            if (!accountGateway.esCuentaDeUsuario(cuentaOrigenNumber, username)) {
                response.setDinError(new DinError("WARNING", "1007", "El usuario no es dueño de la cuenta origen", "Usuario no autorizado"));
                return response;
            }

            BigDecimal monto = request.getDinBody().getMonto();
            Account cuentaActualizada = compraWeb.realizarCompra(cuentaOrigenNumber, monto);

            CompraResponseDTO responseBody = new CompraResponseDTO(
                    encripcionService.encriptar(String.valueOf(cuentaActualizada.getNumber()), symmetricKey, initializationVector),
                    cuentaActualizada.getAmount()
            );
            response.setDinBody(responseBody);

            DinError dinError = new DinError("N", "0000", "Compra realizada exitosamente", "La compra ha sido procesada.");
            response.setDinError(dinError);

        } catch (EncryptionException e) {
            response.setDinError(new DinError("ERROR", "1001", "Error al desencriptar", e.getMessage()));
        } catch (AccountNotFoundException e) {
            response.setDinError(new DinError("WARNING", "1002", "La cuenta no existe", e.getMessage()));
        } catch (Exception e) {
            response.setDinError(new DinError("ERROR", "1006", "Error desconocido", e.getMessage()));
        }

        return response;
    }
}
