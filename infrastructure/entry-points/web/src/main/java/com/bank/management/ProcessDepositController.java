package com.bank.management;

import com.bank.management.data.RequestDepositDTO;
import com.bank.management.data.ResponseDepositDTO;
import com.bank.management.usecase.EncryptionUseCase;
import com.bank.management.usecase.ProcessDepositUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/bank-accounts/deposit")
public class ProcessDepositController {

    private final ProcessDepositUseCase processDepositUseCase;
    private final EncryptionUseCase encryptionUseCase;

    public ProcessDepositController(ProcessDepositUseCase processDepositUseCase, EncryptionUseCase encryptionUseCase) {
        this.processDepositUseCase = processDepositUseCase;
        this.encryptionUseCase = encryptionUseCase;
    }


    @PostMapping
    public ResponseEntity<ResponseDepositDTO> processDeposit(@RequestBody RequestDepositDTO requestDepositDTO) {

        Deposit depositDomain = new Deposit.Builder()
                .customerId(requestDepositDTO.getCustomerId())
                .accountNumber(requestDepositDTO.getAccountNumber())
                .amount(requestDepositDTO.getAmount())
                .type(requestDepositDTO.getType())
                .build();

        Optional<Account> accountOptional = processDepositUseCase.apply(depositDomain);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();

            ResponseDepositDTO responseDepositDTO = new ResponseDepositDTO.Builder()
                    .setAccountNumber(account.getNumber())
                    .setAmount(account.getAmount())
                    .setMessage("Deposit successful")
                    .build();
            responseDepositDTO.setAccountNumber(encryptionUseCase.encryptData(requestDepositDTO.getAccountNumber()));

            return ResponseEntity.ok(responseDepositDTO);
        } else {
            return ResponseEntity.badRequest()
                    .body(new ResponseDepositDTO.Builder()
                            .setAccountNumber(encryptionUseCase.encryptData(requestDepositDTO.getAccountNumber()))
                            .setAmount(requestDepositDTO.getAmount())
                            .setMessage("Deposit failed")
                            .build());
        }
    }


}
