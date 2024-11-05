package com.bank.management;

import com.bank.management.data.RequestWithdrawalDTO;
import com.bank.management.data.ResponseWithdrawalDTO;
import com.bank.management.usecase.EncryptionUseCase;
import com.bank.management.usecase.ProcessWithdrawUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/bank-accounts/withdraw")
public class ProcessWithdrawController {

    private final ProcessWithdrawUseCase processWithdrawUseCase;
    private final EncryptionUseCase encryptionUseCase;

    public ProcessWithdrawController(ProcessWithdrawUseCase processWithdrawUseCase, EncryptionUseCase encryptionUseCase) {
        this.processWithdrawUseCase = processWithdrawUseCase;
        this.encryptionUseCase = encryptionUseCase;
    }


    @PostMapping
    public ResponseEntity<ResponseWithdrawalDTO> processWithdraw(@RequestBody RequestWithdrawalDTO requestWithdrawalDTO) {
        Withdrawal withdrawal = new Withdrawal.Builder()
                .setCustomerId(requestWithdrawalDTO.getCustomerId())
                .setAccountNumber(requestWithdrawalDTO.getAccountNumber())
                .setAmount(requestWithdrawalDTO.getAmount())
                .build();

        Optional<Account> accountOptional = processWithdrawUseCase.apply(withdrawal);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();

            ResponseWithdrawalDTO responseWithdrawalDTO = new ResponseWithdrawalDTO.Builder()
                    .setAccountNumber(encryptionUseCase.encryptData(account.getNumber()))
                    .setAmount(account.getAmount())
                    .setMessage("Withdrawal successful")
                    .build();

            responseWithdrawalDTO.setAccountNumber(encryptionUseCase.encryptData(responseWithdrawalDTO.getAccountNumber()));
            return ResponseEntity.ok(responseWithdrawalDTO);
        } else {
            return ResponseEntity.badRequest()
                    .body(new ResponseWithdrawalDTO.Builder()
                            .setAccountNumber(withdrawal.getAccountNumber())
                            .setAmount(withdrawal.getAmount())
                            .setMessage("Withdrawal failed")
                            .build());
        }
    }
}
