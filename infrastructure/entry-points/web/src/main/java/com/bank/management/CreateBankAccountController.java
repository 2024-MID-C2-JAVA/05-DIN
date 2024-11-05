package com.bank.management;

import com.bank.management.data.RequestCreateAccountDTO;
import com.bank.management.data.ResponseCreateAccountDTO;
import com.bank.management.usecase.CreateBankAccountUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bank-accounts")
public class CreateBankAccountController {

    private final CreateBankAccountUseCase createBankAccountUseCase;

    public CreateBankAccountController(CreateBankAccountUseCase createBankAccountUseCase) {
        this.createBankAccountUseCase = createBankAccountUseCase;
    }

    @PostMapping
    public ResponseEntity<ResponseCreateAccountDTO> createAccount(@RequestBody RequestCreateAccountDTO createAccount) {

        Account accountDomain = new Account.Builder().amount(createAccount.getAmount()).build();

        Customer customerDomain = new Customer.Builder().id(createAccount.getCustomerId()).build();

        Account accountCreated = createBankAccountUseCase.apply(accountDomain, customerDomain);

        ResponseCreateAccountDTO response = new ResponseCreateAccountDTO.Builder().number(accountCreated.getNumber()).message("Account created").build();

        return ResponseEntity.ok(response);
    }

}
