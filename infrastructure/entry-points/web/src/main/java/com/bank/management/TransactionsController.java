package com.bank.management;

import com.bank.management.data.*;
import com.bank.management.din.DinError;
import com.bank.management.din.DinErrorCode;
import com.bank.management.din.RequestMs;
import com.bank.management.din.ResponseMs;
import com.bank.management.usecase.EncryptionUseCase;
import com.bank.management.usecase.ProcessDepositUseCase;
import com.bank.management.usecase.ProcessPurchaseWithCardUseCase;
import com.bank.management.usecase.ProcessWithdrawUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/transactions")
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
    public ResponseEntity<ResponseMs<Map<String, String>>> processDeposit(@RequestBody RequestMs<RequestDepositDTO> request) {

        request.validateDinHeaderFields();

        Deposit depositDomain = new Deposit.Builder()
                .customerId(request.getDinBody().getCustomerId())
                .accountNumber(request.getDinBody().getAccountNumber())
                .amount(request.getDinBody().getAmount())
                .type(request.getDinBody().getType())
                .build();

        Optional<Account> accountOptional = processDepositUseCase.apply(depositDomain);

        Map<String, String> responseData = new HashMap<>();
        responseData.put("accountNumber", encryptionUseCase.encryptData(request.getDinBody().getAccountNumber()));

        DinError dinError = accountOptional.isPresent()
                ? new DinError("T", "api-bank-management-instance-1", DinErrorCode.DEPOSIT_SUCCESS.getCode(), DinErrorCode.DEPOSIT_SUCCESS.getErrorCodeProveedor(), DinErrorCode.DEPOSIT_SUCCESS.getMessage(), "Deposit was successful.")
                : new DinError("E", "api-bank-management-instance-1", DinErrorCode.DEPOSIT_FAILED.getCode(), DinErrorCode.DEPOSIT_FAILED.getErrorCodeProveedor(), DinErrorCode.DEPOSIT_FAILED.getMessage(), "Deposit failed.");

        ResponseMs<Map<String, String>> responseMs = new ResponseMs<>(request.getDinHeader(), responseData, dinError);

        return accountOptional.isPresent() ? ResponseEntity.ok(responseMs) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMs);
    }

    @PostMapping("/purchase-card")
    public ResponseEntity<ResponseMs<Map<String, String>>> processPurchase(@RequestBody RequestMs<RequestPurchaseDTO> request) {

        request.validateDinHeaderFields();

        Purchase purchase = new Purchase.Builder()
                .accountNumber(request.getDinBody().getAccountNumber())
                .amount(request.getDinBody().getAmount())
                .type(request.getDinBody().getType())
                .build();

        Optional<Account> accountOptional = processPurchaseWithCardUseCase.apply(purchase);

        Map<String, String> responseData = new HashMap<>();
        responseData.put("accountNumber", encryptionUseCase.encryptData(request.getDinBody().getAccountNumber()));

        DinError dinError = accountOptional.isPresent()
                ? new DinError("T", "api-bank-management-instance-1", DinErrorCode.PURCHASE_SUCCESS.getCode(), DinErrorCode.PURCHASE_SUCCESS.getErrorCodeProveedor(), DinErrorCode.PURCHASE_SUCCESS.getMessage(), "Purchase was successful.")
                : new DinError("E", "api-bank-management-instance-1", DinErrorCode.PURCHASE_FAILED.getCode(), DinErrorCode.PURCHASE_FAILED.getErrorCodeProveedor(), DinErrorCode.PURCHASE_FAILED.getMessage(), "Purchase failed.");

        ResponseMs<Map<String, String>> responseMs = new ResponseMs<>(request.getDinHeader(), responseData, dinError);

        return accountOptional.isPresent() ? ResponseEntity.ok(responseMs) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMs);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ResponseMs<Map<String, String>>> processWithdraw(@RequestBody RequestMs<RequestWithdrawalDTO> request) {

        request.validateDinHeaderFields();

        Withdrawal withdrawal = new Withdrawal.Builder()
                .setCustomerId(request.getDinBody().getCustomerId())
                .setAccountNumber(request.getDinBody().getAccountNumber())
                .setAmount(request.getDinBody().getAmount())
                .build();

        Optional<Account> accountOptional = processWithdrawUseCase.apply(withdrawal);

        Map<String, String> responseData = new HashMap<>();
        responseData.put("accountNumber", encryptionUseCase.encryptData(request.getDinBody().getAccountNumber()));

        DinError dinError = accountOptional.isPresent()
                ? new DinError("T", "api-bank-management-instance-1", DinErrorCode.WITHDRAWAL_SUCCESS.getCode(), DinErrorCode.WITHDRAWAL_SUCCESS.getErrorCodeProveedor(), DinErrorCode.WITHDRAWAL_SUCCESS.getMessage(), "Withdrawal was successful.")
                : new DinError("E", "api-bank-management-instance-1", DinErrorCode.WITHDRAWAL_FAILED.getCode(), DinErrorCode.WITHDRAWAL_FAILED.getErrorCodeProveedor(), DinErrorCode.WITHDRAWAL_FAILED.getMessage(), "Withdrawal failed.");

        ResponseMs<Map<String, String>> responseMs = new ResponseMs<>(request.getDinHeader(), responseData, dinError);

        return accountOptional.isPresent() ? ResponseEntity.ok(responseMs) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMs);
    }
}
