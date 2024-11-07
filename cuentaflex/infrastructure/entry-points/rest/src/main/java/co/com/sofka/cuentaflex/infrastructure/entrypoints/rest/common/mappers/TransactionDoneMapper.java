package co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.common.mappers;

import co.com.sofka.cuentaflex.business.usecases.common.transactions.TransactionDoneResponse;
import co.com.sofka.cuentaflex.infrastructure.entrypoints.rest.common.dtos.TransactionDoneDto;

public final class TransactionDoneMapper {
    public static TransactionDoneDto fromUseCaseToDtoResponse(TransactionDoneResponse response) {
        return new TransactionDoneDto(response.getTransactionId(), response.getAmount(), response.getCost(), response.getTimestamp());
    }
}
