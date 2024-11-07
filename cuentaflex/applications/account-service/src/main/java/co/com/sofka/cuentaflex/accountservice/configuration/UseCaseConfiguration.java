package co.com.sofka.cuentaflex.accountservice.configuration;

import co.com.sofka.shared.business.usecases.UseCase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "co.com.sofka.cuentaflex.business.usecases",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = UseCase.class)
        }
)
public class UseCaseConfiguration {
}
