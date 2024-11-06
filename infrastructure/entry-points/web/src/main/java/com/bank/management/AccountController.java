package com.bank.management;

import com.bank.management.data.*;
import com.bank.management.usecase.CreateBankAccountUseCase;
import com.bank.management.usecase.DeleteBankAccountUseCase;
import com.bank.management.usecase.GetAccountsByCustomerUseCase;
import com.bank.management.usecase.GetBankAccountUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/bank-accounts")
public class AccountController {

    private final CreateBankAccountUseCase createBankAccountUseCase;
    private final GetAccountsByCustomerUseCase getAccountsByCustomerUseCase;
    private final GetBankAccountUseCase getBankAccountUseCase;
    private final DeleteBankAccountUseCase deleteBankAccountUseCase;

    public AccountController(CreateBankAccountUseCase createBankAccountUseCase, GetAccountsByCustomerUseCase getAccountsByCustomerUseCase, GetBankAccountUseCase getBankAccountUseCase, DeleteBankAccountUseCase deleteBankAccountUseCase) {
        this.createBankAccountUseCase = createBankAccountUseCase;
        this.getAccountsByCustomerUseCase = getAccountsByCustomerUseCase;
        this.getBankAccountUseCase = getBankAccountUseCase;
        this.deleteBankAccountUseCase = deleteBankAccountUseCase;
    }


    @PostMapping
    public ResponseEntity<ResponseCreateAccountDTO> createAccount(@RequestBody RequestCreateAccountDTO createAccount) {

        Account accountDomain = new Account.Builder().amount(createAccount.getAmount()).build();

        Customer customerDomain = new Customer.Builder().id(createAccount.getCustomerId()).build();

        Account accountCreated = createBankAccountUseCase.apply(accountDomain, customerDomain);

        ResponseCreateAccountDTO response = new ResponseCreateAccountDTO.Builder().number(accountCreated.getNumber()).message("Account created").build();

        return ResponseEntity.ok(response);
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


    @PostMapping("/get")
    public BankAccountDTO getBankAccount(@RequestBody RequestGetBankAccountDTO requestGetBankAccountDTO) {
        Account account = getBankAccountUseCase.apply(requestGetBankAccountDTO.getId());

        return new BankAccountDTO.Builder()
                .number(account.getNumber())
                .amount(account.getAmount())
                .build();
    }


    @PostMapping("/delete")
    public ResponseEntity<ResponseDeleteBankAccountDTO> deleteBankAccount(@RequestBody RequestGetBankAccountDTO requestGetBankAccountDTO) {

        boolean isDeleted = deleteBankAccountUseCase.apply(requestGetBankAccountDTO.getId());

        if (isDeleted) {

            ResponseDeleteBankAccountDTO response = new ResponseDeleteBankAccountDTO.Builder()
                    .setMessage("Bank account deleted successfully.")
                    .setAccountNumber(requestGetBankAccountDTO.getId())
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ResponseDeleteBankAccountDTO response = new ResponseDeleteBankAccountDTO.Builder()
                    .setMessage("Error deleting bank account")
                    .setAccountNumber(requestGetBankAccountDTO.getId())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
