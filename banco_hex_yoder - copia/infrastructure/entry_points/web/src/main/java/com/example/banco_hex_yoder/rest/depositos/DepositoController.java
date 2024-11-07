package com.example.banco_hex_yoder.rest.depositos;

import com.example.banco_hex_yoder.din.request.DinRequest;
import com.example.banco_hex_yoder.din.response.DinResponse;
import com.example.banco_hex_yoder.dtos.depositoDTO.DepositoRequestDTO;
import com.example.banco_hex_yoder.dtos.depositoDTO.DepositoResponseDTO;
import com.example.banco_hex_yoder.handlers.depositos.DepositoHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/depositos")
public class DepositoController {

    private final DepositoHandler depositoHandler;

    public DepositoController(DepositoHandler depositoHandler) {
        this.depositoHandler = depositoHandler;
    }

    @PostMapping("/sucursal")
    public DinResponse<DepositoResponseDTO> depositarEnSucursal(@RequestBody DinRequest<DepositoRequestDTO> request) {
        return depositoHandler.ejecutarDepositoSucursal(request);
    }

    @PostMapping("/cajero")
    public DinResponse<DepositoResponseDTO> depositarEnCajero(@RequestBody DinRequest<DepositoRequestDTO> request) {
        return depositoHandler.ejecutarDepositoCajero(request);
    }

    @PostMapping("/otracuenta")
    public DinResponse<DepositoResponseDTO> depositarDesdeOtraCuenta(@RequestBody DinRequest<DepositoRequestDTO> request) {
        return depositoHandler.ejecutarDepositoOtraCuenta(request);
    }
}
