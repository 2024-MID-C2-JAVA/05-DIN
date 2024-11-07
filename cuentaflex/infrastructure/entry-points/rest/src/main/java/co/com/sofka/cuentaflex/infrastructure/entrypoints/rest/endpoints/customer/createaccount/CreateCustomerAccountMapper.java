package co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.endpoints.customer.createaccount;

import co.com.sofka.cuentaflex.business.usecases.customer.createaccount.CreateCustomerAccountRequest;
import co.com.sofka.cuentaflex.business.usecases.customer.createaccount.CreateCustomerAccountResponse;

import java.math.BigDecimal;

public final class CreateCustomerAccountMapper {
    public static CreateCustomerAccountRequest fromDtoToUseCaseRequest(CreateCustomerAccountRequestDto dto) {
        BigDecimal amount = dto.getAmount() == null ? BigDecimal.ZERO : dto.getAmount();
        return new CreateCustomerAccountRequest(dto.getCustomerId(), amount, dto.getInitializationVector(), dto.getSecretKey());
    }

    public static CreateCustomerAccountResponseDto fromUseCaseToDtoResponse(CreateCustomerAccountResponse response) {
        return new CreateCustomerAccountResponseDto(
                response.getAccountId(),
                response.getEncryptedNumber(),
                response.getAmount()
        );
    }
}
