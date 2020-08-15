package com.victorprado.donus.configuration;

import com.victorprado.donus.core.usecase.createaccount.CreateAccountUseCase;
import com.victorprado.donus.core.usecase.performtransaction.PerformTransactionUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rest.bankaccount.BankAccountEndpoint;

@Configuration
public class EndpointConfiguration {

    @Bean
    public BankAccountEndpoint bankAccountEndpoint(CreateAccountUseCase createAccountUseCase, PerformTransactionUseCase performTransactionUseCase) {
        return new BankAccountEndpoint(createAccountUseCase, performTransactionUseCase);
    }
}
