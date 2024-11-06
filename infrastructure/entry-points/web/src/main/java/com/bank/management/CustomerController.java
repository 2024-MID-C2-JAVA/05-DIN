package com.bank.management;

import com.bank.management.data.*;
import com.bank.management.din.*;
import com.bank.management.usecase.CreateCustomerUseCase;
import com.bank.management.usecase.DeleteCustomerUseCase;
import com.bank.management.usecase.GetAllCustomersUseCase;
import com.bank.management.usecase.GetCustomerByIdUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<ResponseMs<Map<String, String>>> createCustomer(@RequestBody RequestMs<RequestCreateCustomerDTO> request) {

        request.validateDinHeaderFields();

        Customer customerDomain = new Customer.Builder()
                .username(request.getDinBody().getUsername())
                .build();

        Optional<Customer> customerCreated = createCustomerUseCase.apply(customerDomain);

        DinErrorCode dinErrorCode = customerCreated.isPresent() ? DinErrorCode.CUSTOMER_CREATED : DinErrorCode.OPERATION_FAILED;
        DinError dinError = new DinError("T", "api-bank-management-instance-1", dinErrorCode.getCode(), dinErrorCode.getErrorCodeProveedor(), dinErrorCode.getMessage(), "Customer creation process completed.");

        Map<String, String> responseData = new HashMap<>();
        responseData.put("username", customerCreated.map(Customer::getUsername).orElse(""));

        ResponseMs<Map<String, String>> responseMs = new ResponseMs<>(request.getDinHeader(), responseData, dinError);

        return ResponseEntity.ok(responseMs);
    }


    @PostMapping("/delete")
    public ResponseEntity<ResponseMs<Map<String, String>>> deleteCustomer(@RequestBody RequestMs<RequestGetCustomerDTO> request) {
        request.validateDinHeaderFields();
        boolean isDeleted = deleteCustomerUseCase.apply(request.getDinBody().getId());

        Map<String, String> responseData = new HashMap<>();
        responseData.put("id", String.valueOf(request.getDinBody().getId()));

        DinErrorCode dinErrorCode = isDeleted ? DinErrorCode.CUSTOMER_DELETED : DinErrorCode.OPERATION_FAILED;
        DinError dinError = new DinError(isDeleted ? "T" : "E", "api-bank-management-instance-1", dinErrorCode.getCode(), dinErrorCode.getErrorCodeProveedor(), dinErrorCode.getMessage(), isDeleted ? "Customer deleted successfully." : "Error deleting customer");

        ResponseMs<Map<String, String>> responseMs = new ResponseMs<>(request.getDinHeader(), responseData, dinError);

        return isDeleted
                ? ResponseEntity.ok(responseMs)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMs);
    }


    @GetMapping
    public ResponseEntity<ResponseMs<List<CustomerDTO>>> getAllCustomers(@RequestBody RequestMs<DinHeader> request) {
        request.validateDinHeaderFields();

        List<Customer> customers = getAllCustomersUseCase.apply();

        List<CustomerDTO> customerDTOs = customers.stream()
                .map(customer -> new CustomerDTO.Builder()
                        .setUsername(customer.getUsername())
                        .build())
                .collect(Collectors.toList());

        DinError dinError = new DinError("T", "api-bank-management-instance-1", DinErrorCode.SUCCESS.getCode(), DinErrorCode.SUCCESS.getErrorCodeProveedor(), DinErrorCode.SUCCESS.getMessage(), "All customers retrieved successfully.");

        ResponseMs<List<CustomerDTO>> responseMs = new ResponseMs<>(request.getDinHeader(), customerDTOs, dinError);

        return ResponseEntity.ok(responseMs);
    }


    @PostMapping("/get")
    public ResponseEntity<ResponseMs<CustomerDTO>> getCustomerById(@RequestBody RequestMs<RequestGetCustomerDTO> request) {

        request.validateDinHeaderFields();

        Customer customer = getCustomerByidUseCase.apply(request.getDinBody().getId());

        if (customer == null) {
            DinError dinError = new DinError("E", "api-bank-management-instance-1", DinErrorCode.CUSTOMER_NOT_FOUND.getCode(), DinErrorCode.CUSTOMER_NOT_FOUND.getErrorCodeProveedor(), DinErrorCode.CUSTOMER_NOT_FOUND.getMessage(), "No customer found with the provided ID.");
            ResponseMs<CustomerDTO> responseMs = new ResponseMs<>(request.getDinHeader(), null, dinError);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMs);
        }

        CustomerDTO customerDTO = new CustomerDTO.Builder()
                .setUsername(customer.getUsername())
                .build();

        DinError dinError = new DinError("T", "api-bank-management-instance-1", DinErrorCode.SUCCESS.getCode(), DinErrorCode.SUCCESS.getErrorCodeProveedor(), DinErrorCode.SUCCESS.getMessage(), "Customer retrieved successfully.");

        ResponseMs<CustomerDTO> responseMs = new ResponseMs<>(request.getDinHeader(), customerDTO, dinError);

        return ResponseEntity.ok(responseMs);
    }
}
