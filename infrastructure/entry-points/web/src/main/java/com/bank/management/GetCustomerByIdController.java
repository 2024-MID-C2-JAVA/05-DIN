package com.bank.management;

import com.bank.management.data.CustomerDTO;
import com.bank.management.data.RequestDepositDTO;
import com.bank.management.data.RequestGetCustomerDTO;
import com.bank.management.usecase.GetCustomerByIdUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class GetCustomerByIdController {

    private final GetCustomerByIdUseCase getCustomerByidUseCase;

    public GetCustomerByIdController(GetCustomerByIdUseCase getCustomerByidUseCase) {
        this.getCustomerByidUseCase = getCustomerByidUseCase;
    }


    @PostMapping("/get")
    public CustomerDTO getAllCustomers(@RequestBody RequestGetCustomerDTO requestGetCustomerDTO) {

        Customer customer = getCustomerByidUseCase.apply(requestGetCustomerDTO.getId());

        return new CustomerDTO.Builder()
                .setUsername(customer.getUsername())
                .build();
    }

}
