package com.victorprado.donus.configuration;

import com.victorprado.donus.core.usecase.createaccount.CreateAccountUseCase;
import com.victorprado.donus.core.usecase.createaccount.ManageBankAccount;
import com.victorprado.donus.core.usecase.createaccount.ManageCustomer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public CreateAccountUseCase createAccountUseCase(ManageCustomer manageCustomer, ManageBankAccount manageBankAccount) {
        return new CreateAccountUseCase(manageCustomer, manageBankAccount);
    }
}
