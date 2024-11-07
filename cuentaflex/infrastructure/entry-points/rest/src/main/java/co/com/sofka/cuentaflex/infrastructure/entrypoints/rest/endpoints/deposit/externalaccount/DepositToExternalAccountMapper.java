package co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.endpoints.deposit.externalaccount;

import co.com.sofka.cuentaflex.business.usecases.deposit.externalaccount.DepositToExternalAccountRequest;
import co.com.sofka.cuentaflex.business.usecases.deposit.externalaccount.DepositToExternalAccountResponse;

public final class DepositToExternalAccountMapper {
    public static DepositToExternalAccountRequest fromDtoToUseCaseRequest(DepositToExternalAccountRequestDto requestDto) {
        return new DepositToExternalAccountRequest(
                requestDto.getCustomerId(),
                requestDto.getAccountId(),
                requestDto.getAmount(),
                requestDto.getEncryptedAccountNumberToDeposit(),
                requestDto.getInitializationVector(),
                requestDto.getSecretKey()
        );
    }

    public static DepositToExternalAccountResponseDto fromUseCaseToDtoResponse(DepositToExternalAccountResponse response) {
        return new DepositToExternalAccountResponseDto(
                response.getTransactionId(),
                response.getAmount(),
                response.getCost(),
                response.getTimestamp(),
                response.getEncryptedPayrollAccountNumber(),
                response.getEncryptedSupplierAccountNumber()
        );
    }
}
