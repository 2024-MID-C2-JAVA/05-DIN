package com.bank.management;

import com.bank.management.data.RequestCreateCustomerDTO;
import com.bank.management.data.ResponseCreateCustomerDTO;
import com.bank.management.usecase.CreateCustomerUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/customers")
public class CreateCustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;

    public CreateCustomerController(CreateCustomerUseCase createCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
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

}
