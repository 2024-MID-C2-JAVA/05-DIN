package com.bank.management;

import com.bank.management.data.BankAccountDTO;
import com.bank.management.data.RequestGetBankAccountDTO;
import com.bank.management.usecase.GetBankAccountUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bank-accounts")
public class GetBankAccountController {

    private final GetBankAccountUseCase getBankAccountUseCase;

    public GetBankAccountController(GetBankAccountUseCase getBankAccountUseCase) {
        this.getBankAccountUseCase = getBankAccountUseCase;
    }


    @PostMapping("/get")
    public BankAccountDTO getBankAccount(@RequestBody RequestGetBankAccountDTO requestGetBankAccountDTO) {
        Account account = getBankAccountUseCase.apply(requestGetBankAccountDTO.getId());

        return new BankAccountDTO.Builder()
                .number(account.getNumber())
                .amount(account.getAmount())
                .build();
    }

}
