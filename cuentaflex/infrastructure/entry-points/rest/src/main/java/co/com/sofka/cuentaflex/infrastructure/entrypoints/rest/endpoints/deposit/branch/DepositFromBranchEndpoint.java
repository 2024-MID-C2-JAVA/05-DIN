package co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.endpoints.deposit.branch;

import co.com.sofka.cuentaflex.business.usecases.common.transactions.TransactionDoneResponse;
import co.com.sofka.cuentaflex.business.usecases.common.transactions.TransactionErrors;
import co.com.sofka.cuentaflex.business.usecases.common.transactions.UnidirectionalTransactionRequest;
import co.com.sofka.cuentaflex.business.usecases.deposit.branch.DepositFromBranchUseCase;
import co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.common.dtos.UnidirectionalTransactionDto;
import co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.common.mappers.TransactionDoneMapper;
import co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.common.mappers.UnidirectionalTransactionMapper;
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
@RequestMapping(AccountEndpointsConstants.BRANCH_DEPOSIT_TO_ACCOUNT_URL)
@Tag(name = "Account Deposits")
public final class DepositFromBranchEndpoint {
    private final DepositFromBranchUseCase depositFromBranchUseCase;

    private static final Map<String, HttpStatus> ERROR_STATUS_MAP = new HashMap<>();

    static {
        ERROR_STATUS_MAP.put(TransactionErrors.ACCOUNT_NOT_FOUND.getCode(), HttpStatus.NOT_FOUND);
        ERROR_STATUS_MAP.put(TransactionErrors.INVALID_AMOUNT.getCode(), HttpStatus.BAD_REQUEST);
    }

    public DepositFromBranchEndpoint(DepositFromBranchUseCase depositFromBranchUseCase) {
        this.depositFromBranchUseCase = depositFromBranchUseCase;
    }

    @PostMapping
    public ResponseEntity<?> deposit(@RequestBody UnidirectionalTransactionDto requestDto) {
        UnidirectionalTransactionRequest request = UnidirectionalTransactionMapper.fromDtoToUseCaseRequest(requestDto);

        ResultWith<TransactionDoneResponse> result = this.depositFromBranchUseCase.execute(request);

        if (result.isFailure()) {
            HttpStatus status = ERROR_STATUS_MAP.getOrDefault(
                    result.getError().getCode(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );

            return ResponseEntity.status(status).body(ErrorMapper.fromUseCaseToDtoError(result.getError()));
        }

        return ResponseEntity.ok(TransactionDoneMapper.fromUseCaseToDtoResponse(result.getValue()));
    }
}
