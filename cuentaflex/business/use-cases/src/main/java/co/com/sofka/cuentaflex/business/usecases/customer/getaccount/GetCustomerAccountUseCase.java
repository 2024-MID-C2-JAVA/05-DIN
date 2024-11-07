package co.com.sofka.cuentaflex.business.usecases.customer.getaccount;

import co.com.sofka.core.cryptography.aes.AESCipher;
import co.com.sofka.cuentaflex.business.drivenports.repositories.AccountRepository;
import co.com.sofka.cuentaflex.business.models.Account;
import co.com.sofka.shared.business.usecases.ResultWith;
import co.com.sofka.shared.business.usecases.UseCase;

public final class GetCustomerAccountUseCase implements UseCase<GetCustomerAccountRequest, ResultWith<GetCustomerAccountResponse>> {
    private final AccountRepository accountRepository;

    public GetCustomerAccountUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public ResultWith<GetCustomerAccountResponse> execute(GetCustomerAccountRequest request) {
        Account account = accountRepository.getByIdAndCustomerId(request.getAccountId(), request.getCustomerId());

        if (account == null) {
            return ResultWith.failure(GetCustomerAccountErrors.ACCOUNT_NOT_FOUND);
        }

        return ResultWith.success(new GetCustomerAccountResponse(
                account.getId(),
                AESCipher.encryptToBase64(String.valueOf(account.getNumber()), request.getSecretKey(), request.getInitializationVector()),
                account.getAmount()
        ));
    }
}
