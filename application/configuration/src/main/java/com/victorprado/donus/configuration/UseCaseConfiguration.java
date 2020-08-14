package com.victorprado.donus.configuration;

import com.victorprado.donus.core.usecase.createaccount.CreateAccountUseCase;
import com.victorprado.donus.core.usecase.createaccount.CreateBankAccount;
import com.victorprado.donus.core.usecase.createaccount.GetBankAccount;
import com.victorprado.donus.core.usecase.createaccount.GetCustomer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public CreateAccountUseCase createAccountUseCase(GetCustomer getCustomer, CreateBankAccount createBankAccount, GetBankAccount getBankAccount) {
        return new CreateAccountUseCase(getCustomer, createBankAccount, getBankAccount);
    }
}
