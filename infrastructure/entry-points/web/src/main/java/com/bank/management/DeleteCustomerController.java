package com.bank.management;

import com.bank.management.data.RequestGetCustomerDTO;
import com.bank.management.data.ResponseDeleteBankAccountDTO;
import com.bank.management.usecase.CreateCustomerUseCase;
import com.bank.management.usecase.DeleteCustomerUseCase;
import com.bank.management.usecase.GetAllCustomersUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class DeleteCustomerController {

    private final DeleteCustomerUseCase deleteCustomerUseCase;

    public DeleteCustomerController(DeleteCustomerUseCase deleteCustomerUseCase) {
        this.deleteCustomerUseCase = deleteCustomerUseCase;
    }

    @PostMapping("/delete")
    public ResponseEntity<ResponseDeleteBankAccountDTO> deleteCustomer(@RequestBody RequestGetCustomerDTO requestGetCustomerDTO) {
        boolean isDeleted = deleteCustomerUseCase.apply(requestGetCustomerDTO.getId());

        if (isDeleted) {
            ResponseDeleteBankAccountDTO response = new ResponseDeleteBankAccountDTO.Builder()
                    .setMessage("Customer deleted successfully.")
                    .setAccountNumber(String.valueOf(requestGetCustomerDTO.getId()))
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ResponseDeleteBankAccountDTO response = new ResponseDeleteBankAccountDTO.Builder()
                    .setMessage("Error deleting customer")
                    .setAccountNumber(String.valueOf(requestGetCustomerDTO.getId()))
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
