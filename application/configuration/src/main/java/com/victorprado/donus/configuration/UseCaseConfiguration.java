package com.victorprado.donus.configuration;

import com.victorprado.donus.core.usecase.createaccount.CreateAccountUseCase;
import com.victorprado.donus.core.usecase.createaccount.CreateBankAccount;
import com.victorprado.donus.core.usecase.createcustomer.CreateCustomer;
import com.victorprado.donus.core.usecase.createcustomer.CreateCustomerUseCase;
import com.victorprado.donus.core.usecase.createaccount.GetCustomer;
import com.victorprado.donus.core.usecase.createcustomer.UpdateCustomer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public CreateAccountUseCase createAccountUseCase(GetCustomer getCustomer, CreateBankAccount createBankAccount) {
        return new CreateAccountUseCase(getCustomer, createBankAccount);
    }

    @Bean
    public CreateCustomerUseCase createCustomerUseCase(GetCustomer getCustomer, CreateCustomer createCustomer, UpdateCustomer updateCustomer) {
        return new CreateCustomerUseCase(getCustomer, createCustomer, updateCustomer);
    }
}
