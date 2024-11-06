package com.bank.management;

import com.bank.management.data.*;
import com.bank.management.usecase.CreateCustomerUseCase;
import com.bank.management.usecase.DeleteCustomerUseCase;
import com.bank.management.usecase.GetAllCustomersUseCase;
import com.bank.management.usecase.GetCustomerByIdUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final GetAllCustomersUseCase getAllCustomersUseCase;
    private final GetCustomerByIdUseCase getCustomerByidUseCase;

    public CustomerController(CreateCustomerUseCase createCustomerUseCase, DeleteCustomerUseCase deleteCustomerUseCase, GetAllCustomersUseCase getAllCustomersUseCase, GetCustomerByIdUseCase getCustomerByidUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
        this.getAllCustomersUseCase = getAllCustomersUseCase;
        this.getCustomerByidUseCase = getCustomerByidUseCase;
    }

    @PostMapping
    public ResponseEntity<ResponseCreateCustomerDTO> createCustomer(@RequestBody RequestCreateCustomerDTO RequestCustomerDTO) {

        Customer customerDomain = new Customer.Builder().username(RequestCustomerDTO.getUsername()).build();
        Optional<Customer> customerCreated = createCustomerUseCase.apply(customerDomain);

        return customerCreated.map(customer -> ResponseEntity.ok(new ResponseCreateCustomerDTO.Builder()
                .username(customer.getUsername())
                .message("Customer created")
                .build())).orElseGet(() -> ResponseEntity.badRequest()
                .body(new ResponseCreateCustomerDTO.Builder()
                        .username("")
                        .message("Customer not created")
                        .build()));
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


    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = getAllCustomersUseCase.apply();

        return customers.stream()
                .map(customer -> new CustomerDTO.Builder()
                        .setUsername(customer.getUsername())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/get")
    public CustomerDTO getAllCustomers(@RequestBody RequestGetCustomerDTO requestGetCustomerDTO) {

        Customer customer = getCustomerByidUseCase.apply(requestGetCustomerDTO.getId());

        return new CustomerDTO.Builder()
                .setUsername(customer.getUsername())
                .build();

    }
}
