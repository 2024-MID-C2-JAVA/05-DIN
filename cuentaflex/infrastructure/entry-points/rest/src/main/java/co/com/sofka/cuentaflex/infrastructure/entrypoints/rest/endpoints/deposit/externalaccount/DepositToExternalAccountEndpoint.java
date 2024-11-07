package co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.endpoints.deposit.externalaccount;

import co.com.sofka.cuentaflex.business.usecases.common.transactions.TransactionErrors;
import co.com.sofka.cuentaflex.business.usecases.deposit.externalaccount.DepositToExternalAccountRequest;
import co.com.sofka.cuentaflex.business.usecases.deposit.externalaccount.DepositToExternalAccountResponse;
import co.com.sofka.cuentaflex.business.usecases.deposit.externalaccount.DepositToExternalAccountUseCase;
import co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.constants.AccountEndpointsConstants;
import co.com.sofka.shared.business.usecases.ResultWith;
import co.com.sofka.shared.infrastructure.entrypoints.rest.ErrorMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(AccountEndpointsConstants.EXTERNAL_DEPOSIT_TO_ACCOUNT_URL)
@Tag(name = "Account Deposits")
public final class DepositToExternalAccountEndpoint {
    private final DepositToExternalAccountUseCase depositToExternalAccountUseCase;

    private final static Map<String, HttpStatus> ERROR_STATUS_MAP = new HashMap<>();

    static {
        ERROR_STATUS_MAP.put(TransactionErrors.ACCOUNT_NOT_FOUND.getCode(), HttpStatus.NOT_FOUND);
        ERROR_STATUS_MAP.put(TransactionErrors.INVALID_AMOUNT.getCode(), HttpStatus.BAD_REQUEST);
        ERROR_STATUS_MAP.put(TransactionErrors.INSUFFICIENT_FUNDS.getCode(), HttpStatus.BAD_REQUEST);
    }

    public DepositToExternalAccountEndpoint(DepositToExternalAccountUseCase depositToExternalAccountUseCase) {
        this.depositToExternalAccountUseCase = depositToExternalAccountUseCase;
    }

    @PostMapping
    public ResponseEntity<?> deposit(@RequestBody DepositToExternalAccountRequestDto requestDto) {
        DepositToExternalAccountRequest useCaseRequest = DepositToExternalAccountMapper.fromDtoToUseCaseRequest(requestDto);
        ResultWith<DepositToExternalAccountResponse> useCaseResponse = this.depositToExternalAccountUseCase.execute(useCaseRequest);

        if(useCaseResponse.isFailure()) {
            HttpStatus status = ERROR_STATUS_MAP.getOrDefault(
                    useCaseResponse.getError().getCode(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
            return ResponseEntity.status(status).body(ErrorMapper.fromUseCaseToDtoError(useCaseResponse.getError()));
        }

        return ResponseEntity.ok(DepositToExternalAccountMapper.fromUseCaseToDtoResponse(useCaseResponse.getValue()));
    }
}
