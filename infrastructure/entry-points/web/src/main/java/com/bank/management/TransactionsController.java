package com.bank.management;

import com.bank.management.data.*;
import com.bank.management.usecase.EncryptionUseCase;
import com.bank.management.usecase.ProcessDepositUseCase;
import com.bank.management.usecase.ProcessPurchaseWithCardUseCase;
import com.bank.management.usecase.ProcessWithdrawUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bank-accounts/transaction")
public class TransactionsController {

    private final EncryptionUseCase encryptionUseCase;
    private final ProcessDepositUseCase processDepositUseCase;
    private final ProcessPurchaseWithCardUseCase processPurchaseWithCardUseCase;
    private final ProcessWithdrawUseCase processWithdrawUseCase;

    public TransactionsController(EncryptionUseCase encryptionUseCase, ProcessDepositUseCase processDepositUseCase, ProcessPurchaseWithCardUseCase processPurchaseWithCardUseCase, ProcessWithdrawUseCase processWithdrawUseCase) {
        this.encryptionUseCase = encryptionUseCase;
        this.processDepositUseCase = processDepositUseCase;
        this.processPurchaseWithCardUseCase = processPurchaseWithCardUseCase;
        this.processWithdrawUseCase = processWithdrawUseCase;
    }


    @PostMapping("/deposit")
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

    @PostMapping("/purchase-card")
    public ResponseEntity<ResponsePurchaseDTO> processPurchase(@RequestBody RequestPurchaseDTO purchaseDTO) {
        Purchase purchase = new Purchase.Builder()
                .accountNumber(purchaseDTO.getAccountNumber())
                .amount(purchaseDTO.getAmount())
                .type(purchaseDTO.getType())
                .build();

        Optional<Account> accountOptional = processPurchaseWithCardUseCase.apply(purchase);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();

            ResponsePurchaseDTO responsePurchaseDTO = new ResponsePurchaseDTO.Builder()
                    .setAccountNumber(encryptionUseCase.encryptData(account.getNumber()))
                    .setAmount(account.getAmount())
                    .setMessage("Purchase successful")
                    .build();

            responsePurchaseDTO.setAccountNumber(encryptionUseCase.encryptData(responsePurchaseDTO.getAccountNumber()));
            return ResponseEntity.ok(responsePurchaseDTO);
        } else {
            return ResponseEntity.badRequest()
                    .body(new ResponsePurchaseDTO.Builder()
                            .setAccountNumber(purchase.getAccountNumber())
                            .setAmount(purchase.getAmount())
                            .setMessage("Purchase failed")
                            .build());
        }
    }

    @PostMapping("/purchase")
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
