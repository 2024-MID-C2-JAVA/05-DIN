package co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.endpoints.customer.create;

import co.com.sofka.cuentaflex.business.usecases.customer.create.CreateCustomerRequest;
import co.com.sofka.cuentaflex.business.usecases.customer.create.CreateCustomerResponse;
import org.springframework.stereotype.Component;

@Component
public final class CreateCustomerMapper {
    public static CreateCustomerRequest fromDtoToUseCaseRequest(CreateCustomerRequestDto requestDto) {
        return new CreateCustomerRequest(requestDto.getUsername());
    }

    public static CreateCustomerResponseDto fromUseCaseToDtoResponse(CreateCustomerResponse response) {
        return new CreateCustomerResponseDto(response.getCustomerId(), response.getUsername());
    }
}
