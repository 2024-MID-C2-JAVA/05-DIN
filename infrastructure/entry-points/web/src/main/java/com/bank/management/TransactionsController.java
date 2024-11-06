package com.bank.management;

import com.bank.management.data.*;
import com.bank.management.exception.*;
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

        try {
            Optional<Account> accountOptional = processDepositUseCase.apply(depositDomain);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("accountNumber", encryptionUseCase.encryptData(request.getDinBody().getAccountNumber()));

            return ResponseBuilder.buildResponse(
                    request.getDinHeader(),
                    responseData,
                    DinErrorCode.DEPOSIT_SUCCESS,
                    HttpStatus.OK,
                    "Deposit was successful."
            );

        } catch (InvalidAmountException e) {
            return ResponseBuilder.buildResponse(
                    request.getDinHeader(),
                    null,
                    DinErrorCode.DEPOSIT_FAILED,
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );

        } catch (BankAccountNotFoundException e) {
            return ResponseBuilder.buildResponse(
                    request.getDinHeader(),
                    null,
                    DinErrorCode.ACCOUNT_NOT_FOUND,
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );

        } catch (CustomerNotFoundException e) {
            return ResponseBuilder.buildResponse(
                    request.getDinHeader(),
                    null,
                    DinErrorCode.CUSTOMER_NOT_FOUND,
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );

        } catch (InvalidDepositTypeException e) {
            return ResponseBuilder.buildResponse(
                    request.getDinHeader(),
                    null,
                    DinErrorCode.DEPOSIT_FAILED,
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );

        } catch (Exception e) {
            return ResponseBuilder.buildResponse(
                    request.getDinHeader(),
                    null,
                    DinErrorCode.DEPOSIT_FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );
        }
    }


    @PostMapping("/purchase-card")
    public ResponseEntity<ResponseMs<Map<String, String>>> processPurchase(@RequestBody RequestMs<RequestPurchaseDTO> request) {

        request.validateDinHeaderFields();

        Purchase purchase = new Purchase.Builder()
                .accountNumber(request.getDinBody().getAccountNumber())
                .amount(request.getDinBody().getAmount())
                .type(request.getDinBody().getType())
                .build();

        try {
            Optional<Account> accountOptional = processPurchaseWithCardUseCase.apply(purchase);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("accountNumber", encryptionUseCase.encryptData(request.getDinBody().getAccountNumber()));

            return ResponseBuilder.buildResponse(
                    request.getDinHeader(),
                    responseData,
                    DinErrorCode.PURCHASE_SUCCESS,
                    HttpStatus.OK,
                    "Purchase was successful."
            );

        } catch (BankAccountNotFoundException e) {
            return ResponseBuilder.buildResponse(
                    request.getDinHeader(),
                    null,
                    DinErrorCode.ACCOUNT_NOT_FOUND,
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );

        } catch (CustomerNotFoundException e) {
            return ResponseBuilder.buildResponse(
                    request.getDinHeader(),
                    null,
                    DinErrorCode.CUSTOMER_NOT_FOUND,
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );

        } catch (InvalidPurchaseTypeException e) {
            return ResponseBuilder.buildResponse(
                    request.getDinHeader(),
                    null,
                    DinErrorCode.PURCHASE_FAILED,
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );

        } catch (InsufficientFundsException e) {
            return ResponseBuilder.buildResponse(
                    request.getDinHeader(),
                    null,
                    DinErrorCode.PURCHASE_FAILED,
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );

        } catch (Exception e) {
            return ResponseBuilder.buildResponse(
                    request.getDinHeader(),
                    null,
                    DinErrorCode.PURCHASE_FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );
        }
    }


    @PostMapping("/withdraw")
    public ResponseEntity<ResponseMs<Map<String, String>>> processWithdraw(@RequestBody RequestMs<RequestWithdrawalDTO> request) {

        request.validateDinHeaderFields();

        Withdrawal withdrawal = new Withdrawal.Builder()
                .setCustomerId(request.getDinBody().getCustomerId())
                .setAccountNumber(request.getDinBody().getAccountNumber())
                .setAmount(request.getDinBody().getAmount())
                .build();

        try {
            Optional<Account> accountOptional = processWithdrawUseCase.apply(withdrawal);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("accountNumber", encryptionUseCase.encryptData(request.getDinBody().getAccountNumber()));

            return ResponseBuilder.buildResponse(
                    request.getDinHeader(),
                    responseData,
                    DinErrorCode.WITHDRAWAL_SUCCESS,
                    HttpStatus.OK,
                    "Withdrawal was successful."
            );

        } catch (InvalidAmountException e) {
            return ResponseBuilder.buildResponse(
                    request.getDinHeader(),
                    null,
                    DinErrorCode.WITHDRAWAL_FAILED,
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );

        } catch (BankAccountNotFoundException e) {
            return ResponseBuilder.buildResponse(
                    request.getDinHeader(),
                    null,
                    DinErrorCode.ACCOUNT_NOT_FOUND,
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );

        } catch (CustomerNotFoundException e) {
            return ResponseBuilder.buildResponse(
                    request.getDinHeader(),
                    null,
                    DinErrorCode.CUSTOMER_NOT_FOUND,
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );

        } catch (InsufficientFundsException e) {
            return ResponseBuilder.buildResponse(
                    request.getDinHeader(),
                    null,
                    DinErrorCode.WITHDRAWAL_FAILED,
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );

        } catch (Exception e) {
            return ResponseBuilder.buildResponse(
                    request.getDinHeader(),
                    null,
                    DinErrorCode.WITHDRAWAL_FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );
        }
    }

}
