package com.bank.management.config;

import com.bank.management.gateway.CustomerRepository;
import com.bank.management.usecase.GetAllCustomersUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "com.bank.management.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        }
)
public class UseCaseConfig {

}