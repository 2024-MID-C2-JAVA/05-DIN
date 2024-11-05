package com.bank.management;

import com.bank.management.data.CustomerDTO;
import com.bank.management.usecase.GetAllCustomersUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customers")
public class GetAllCustomersController {

    private final GetAllCustomersUseCase getAllCustomersUseCase;

    public GetAllCustomersController(GetAllCustomersUseCase getAllCustomersUseCase) {
        this.getAllCustomersUseCase = getAllCustomersUseCase;
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

}
