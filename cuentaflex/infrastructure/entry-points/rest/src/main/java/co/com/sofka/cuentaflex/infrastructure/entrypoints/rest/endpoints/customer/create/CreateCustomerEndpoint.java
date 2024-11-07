package co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.endpoints.customer.create;

import co.com.sofka.cuentaflex.business.usecases.customer.create.CreateCustomerRequest;
import co.com.sofka.cuentaflex.business.usecases.customer.create.CreateCustomerResponse;
import co.com.sofka.cuentaflex.business.usecases.customer.create.CreateCustomerUseCase;
import co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.constants.CustomerEndpointsConstants;
import co.com.sofka.shared.business.usecases.ResultWith;
import co.com.sofka.shared.infrastructure.entrypoints.din.DinRequest;
import co.com.sofka.shared.infrastructure.entrypoints.din.DinResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CustomerEndpointsConstants.CREATE_CUSTOMER_ENDPOINT)
@Tag(name = "Customer")
public final class CreateCustomerEndpoint {
    private final CreateCustomerUseCase createCustomerUseCase;

    public CreateCustomerEndpoint(CreateCustomerUseCase createCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
    }

    @PostMapping
    public ResponseEntity<DinResponse<CreateCustomerResponseDto>> handle(
            @RequestBody DinRequest<CreateCustomerRequestDto> createCustomerRequestDto
    ) {
        CreateCustomerRequest createCustomerRequest = CreateCustomerMapper.fromDinToUseCaseRequest(createCustomerRequestDto);

        ResultWith<CreateCustomerResponse> result = this.createCustomerUseCase.execute(createCustomerRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CreateCustomerMapper.fromUseCaseToDinResponse(
                        createCustomerRequestDto.getDinHeader(),
                        result.getValue())
                );
    }
}
