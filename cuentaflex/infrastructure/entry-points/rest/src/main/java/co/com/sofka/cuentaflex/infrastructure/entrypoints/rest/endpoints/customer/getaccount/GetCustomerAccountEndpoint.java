package co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.endpoints.customer.getaccount;

import co.com.sofka.cuentaflex.business.usecases.customer.createaccount.CreateCustomerAccountErrors;
import co.com.sofka.cuentaflex.business.usecases.customer.getaccount.GetCustomerAccountErrors;
import co.com.sofka.cuentaflex.business.usecases.customer.getaccount.GetCustomerAccountRequest;
import co.com.sofka.cuentaflex.business.usecases.customer.getaccount.GetCustomerAccountResponse;
import co.com.sofka.cuentaflex.business.usecases.customer.getaccount.GetCustomerAccountUseCase;
import co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.constants.CustomerEndpointsConstants;
import co.com.sofka.shared.business.usecases.ResultWith;
import co.com.sofka.shared.infrastructure.entrypoints.rest.ErrorMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(CustomerEndpointsConstants.GET_CUSTOMER_ACCOUNT)
@Tag(name = "Customer Account")
public final class GetCustomerAccountEndpoint {
    private final GetCustomerAccountUseCase getCustomerAccountUseCase;

    private static final Map<String, HttpStatus> ERROR_STATUS_MAP = new HashMap<>();

    static {
        ERROR_STATUS_MAP.put(GetCustomerAccountErrors.ACCOUNT_NOT_FOUND.getCode(), HttpStatus.NOT_FOUND);
    }

    public GetCustomerAccountEndpoint(GetCustomerAccountUseCase getCustomerAccountUseCase) {
        this.getCustomerAccountUseCase = getCustomerAccountUseCase;
    }

    @GetMapping
    public ResponseEntity<?> getCustomerAccount(@RequestBody GetCustomerAccountRequestDto request) {
        GetCustomerAccountRequest useCaseRequest = GetCustomerAccountMapper.fromDtoToUseCaseRequest(request);
        ResultWith<GetCustomerAccountResponse> useCaseResponse = this.getCustomerAccountUseCase.execute(useCaseRequest);

        if(useCaseResponse.isFailure()) {
            HttpStatus status = ERROR_STATUS_MAP.getOrDefault(
                    useCaseResponse.getError().getCode(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );

            return ResponseEntity.status(status).body(ErrorMapper.fromUseCaseToDtoError(useCaseResponse.getError()));
        }

        return ResponseEntity.ok(GetCustomerAccountMapper.fromUseCaseToDtoResponse(useCaseResponse.getValue()));
    }
}
