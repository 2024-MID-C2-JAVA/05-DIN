package co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.endpoints.customer.create;

import co.com.sofka.cuentaflex.domain.usecases.customer.create.CreateCustomerRequest;
import co.com.sofka.cuentaflex.domain.usecases.customer.create.CreateCustomerResponse;
import co.com.sofka.shared.infrastructure.entrypoints.din.DinHeader;
import co.com.sofka.shared.infrastructure.entrypoints.din.DinRequest;
import co.com.sofka.shared.infrastructure.entrypoints.din.DinResponse;
import org.springframework.stereotype.Component;

@Component
public final class CreateCustomerMapper {
    public static CreateCustomerRequest fromDinToUseCaseRequest(DinRequest<CreateCustomerRequestDto> requestDto) {
        return new CreateCustomerRequest(requestDto.getDinBody().getUsername());
    }

    public static DinResponse<CreateCustomerResponseDto> fromUseCaseToDinResponse(
            DinHeader dinHeader,
            CreateCustomerResponse response
    ) {
        CreateCustomerResponseDto responseDto = new CreateCustomerResponseDto(response.getCustomerId(), response.getUsername());
        return new DinResponse<>(dinHeader, responseDto);
    }
}
