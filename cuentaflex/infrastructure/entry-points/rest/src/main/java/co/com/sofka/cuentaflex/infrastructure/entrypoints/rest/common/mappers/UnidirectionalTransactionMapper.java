package co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.common.mappers;

import co.com.sofka.cuentaflex.business.usecases.common.transactions.UnidirectionalTransactionRequest;
import co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.common.dtos.UnidirectionalTransactionDto;

public final class UnidirectionalTransactionMapper {
    public static UnidirectionalTransactionRequest fromDtoToUseCaseRequest(UnidirectionalTransactionDto dto) {
        return new UnidirectionalTransactionRequest(dto.getCustomerId(), dto.getAccountId(), dto.getAmount());
    }
}
