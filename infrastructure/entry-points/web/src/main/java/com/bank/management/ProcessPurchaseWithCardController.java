package com.bank.management;

import com.bank.management.data.RequestPurchaseDTO;
import com.bank.management.data.ResponsePurchaseDTO;
import com.bank.management.usecase.EncryptionUseCase;
import com.bank.management.usecase.ProcessPurchaseWithCardUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/bank-accounts/purchase-card")
public class ProcessPurchaseWithCardController {

    private final ProcessPurchaseWithCardUseCase processPurchaseWithCardUseCase;
    private final EncryptionUseCase encryptionUseCase;

    public ProcessPurchaseWithCardController(ProcessPurchaseWithCardUseCase processPurchaseWithCardUseCase, EncryptionUseCase encryptionUseCase) {
        this.processPurchaseWithCardUseCase = processPurchaseWithCardUseCase;
        this.encryptionUseCase = encryptionUseCase;
    }


    @PostMapping
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

}
