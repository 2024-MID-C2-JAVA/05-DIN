package com.bank.management;

import com.bank.management.data.BankAccountDTO;
import com.bank.management.data.RequestGetBankAccountDTO;
import com.bank.management.usecase.GetAccountsByCustomerUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/bank-accounts/customer")
public class GetAccountsByCustomerIdController {

    private final GetAccountsByCustomerUseCase getAccountsByCustomerUseCase;

    public GetAccountsByCustomerIdController(GetAccountsByCustomerUseCase getAccountsByCustomerUseCase) {
        this.getAccountsByCustomerUseCase = getAccountsByCustomerUseCase;
    }

    @PostMapping("/get-accounts")
    public List<BankAccountDTO> getBankAccountByCustomer(@RequestBody RequestGetBankAccountDTO requestGetBankAccountDTO) {
        List<Account> accounts = getAccountsByCustomerUseCase.apply(requestGetBankAccountDTO.getId());

        return accounts.stream()
                .map(account -> new BankAccountDTO.Builder()
                        .number(account.getNumber())
                        .amount(account.getAmount())
                        .build())
                .collect(Collectors.toList());
    }

}
