package com.victorprado.donus.configuration;

import com.victorprado.donus.core.usecase.createaccount.CreateAccountUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rest.bankaccount.BankAccountEndpoint;

@Configuration
public class EndpointConfiguration {

    @Bean
    public BankAccountEndpoint bankAccountEndpoint(CreateAccountUseCase createAccountUseCase) {
        return new BankAccountEndpoint(createAccountUseCase);
    }
}
