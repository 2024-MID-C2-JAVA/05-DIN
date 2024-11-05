package com.bank.management;

import com.bank.management.data.RequestGetBankAccountDTO;
import com.bank.management.data.ResponseDeleteBankAccountDTO;
import com.bank.management.usecase.DeleteBankAccountUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bank-accounts")
public class DeleteBankAccountController {

    private final DeleteBankAccountUseCase deleteBankAccountUseCase;

    public DeleteBankAccountController(DeleteBankAccountUseCase deleteBankAccountUseCase) {
        this.deleteBankAccountUseCase = deleteBankAccountUseCase;
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
