package co.com.sofka.shared.infrastructure.entrypoints.rest;

import co.com.sofka.shared.business.usecases.Error;

public final class ErrorMapper {
    public static ErrorDto fromUseCaseToDtoError(Error error) {
        return new ErrorDto(error.getCode(), error.getMessage());
    }
}
