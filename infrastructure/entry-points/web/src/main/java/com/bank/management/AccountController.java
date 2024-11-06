package com.bank.management;

import com.bank.management.data.*;
import com.bank.management.din.DinError;
import com.bank.management.din.DinErrorCode;
import com.bank.management.din.RequestMs;
import com.bank.management.din.ResponseMs;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<ResponseMs<Map<String, String>>> createAccount(@RequestBody RequestMs<RequestCreateAccountDTO> request) {

        request.validateDinHeaderFields();

        Account accountDomain = new Account.Builder().amount(request.getDinBody().getAmount()).build();
        Customer customerDomain = new Customer.Builder().id(request.getDinBody().getCustomerId()).build();

        Account accountCreated = createBankAccountUseCase.apply(accountDomain, customerDomain);

        DinErrorCode dinErrorCode = accountCreated != null ? DinErrorCode.ACCOUNT_CREATED : DinErrorCode.ERROR_CREATING_ACCOUNT;
        DinError dinError = new DinError("T", "api-bank-management-instance-1", dinErrorCode.getCode(), dinErrorCode.getErrorCodeProveedor(), dinErrorCode.getMessage(), "Account creation process completed.");

        Map<String, String> responseData = new HashMap<>();
        responseData.put("accountNumber", accountCreated != null ? accountCreated.getNumber() : "");

        ResponseMs<Map<String, String>> responseMs = new ResponseMs<>(request.getDinHeader(), responseData, dinError);
        return accountCreated != null ? ResponseEntity.ok(responseMs) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMs);
    }

    @PostMapping("/customer/get-accounts")
    public ResponseEntity<ResponseMs<List<BankAccountDTO>>> getBankAccountByCustomer(@RequestBody RequestMs<RequestGetBankAccountDTO> request) {

        request.validateDinHeaderFields();

        List<Account> accounts = getAccountsByCustomerUseCase.apply(request.getDinBody().getId());

        List<BankAccountDTO> accountDTOs = accounts.stream()
                .map(account -> new BankAccountDTO.Builder()
                        .number(account.getNumber())
                        .amount(account.getAmount())
                        .build())
                .collect(Collectors.toList());

        DinError dinError = new DinError("T", "api-bank-management-instance-1", DinErrorCode.SUCCESS.getCode(), DinErrorCode.SUCCESS.getErrorCodeProveedor(), DinErrorCode.SUCCESS.getMessage(), "Customer accounts were retrieved successfully.");
        ResponseMs<List<BankAccountDTO>> responseMs = new ResponseMs<>(request.getDinHeader(), accountDTOs, dinError);

        return ResponseEntity.ok(responseMs);
    }

    @PostMapping("/get")
    public ResponseEntity<ResponseMs<BankAccountDTO>> getBankAccount(@RequestBody RequestMs<RequestGetBankAccountDTO> request) {

        request.validateDinHeaderFields();

        Account account = getBankAccountUseCase.apply(request.getDinBody().getId());

        if (account == null) {
            DinError dinError = new DinError("E", "api-bank-management-instance-1", DinErrorCode.ACCOUNT_NOT_FOUND.getCode(), DinErrorCode.ACCOUNT_NOT_FOUND.getErrorCodeProveedor(), DinErrorCode.ACCOUNT_NOT_FOUND.getMessage(), "No account found with the provided ID.");
            ResponseMs<BankAccountDTO> responseMs = new ResponseMs<>(request.getDinHeader(), null, dinError);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMs);
        }

        BankAccountDTO accountDTO = new BankAccountDTO.Builder()
                .number(account.getNumber())
                .amount(account.getAmount())
                .build();

        DinError dinError = new DinError("T", "api-bank-management-instance-1", DinErrorCode.SUCCESS.getCode(), DinErrorCode.SUCCESS.getErrorCodeProveedor(), DinErrorCode.SUCCESS.getMessage(), "Account information was retrieved successfully.");
        ResponseMs<BankAccountDTO> responseMs = new ResponseMs<>(request.getDinHeader(), accountDTO, dinError);

        return ResponseEntity.ok(responseMs);
    }

    @PostMapping("/delete")
    public ResponseEntity<ResponseMs<Map<String, String>>> deleteBankAccount(@RequestBody RequestMs<RequestGetBankAccountDTO> request) {

        request.validateDinHeaderFields();

        boolean isDeleted = deleteBankAccountUseCase.apply(request.getDinBody().getId());
        Map<String, String> responseData = new HashMap<>();
        responseData.put("accountNumber", request.getDinBody().getId());

        DinErrorCode dinErrorCode = isDeleted ? DinErrorCode.ACCOUNT_DELETED : DinErrorCode.ERROR_DELETING_ACCOUNT;
        DinError dinError = new DinError(isDeleted ? "T" : "E", "api-bank-management-instance-1", dinErrorCode.getCode(), dinErrorCode.getErrorCodeProveedor(), dinErrorCode.getMessage(), dinErrorCode.getMessage());

        ResponseMs<Map<String, String>> responseMs = new ResponseMs<Map<String, String>>(request.getDinHeader(), responseData, dinError);

        return isDeleted
                ? ResponseEntity.ok(responseMs)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMs);
    }
}
