package com.victorprado.donus.configuration;

import com.victorprado.donus.core.usecase.createaccount.CreateAccountUseCase;
import com.victorprado.donus.core.usecase.createaccount.CreateBankAccount;
import com.victorprado.donus.core.usecase.createaccount.GetBankAccount;
import com.victorprado.donus.core.usecase.createaccount.GetCustomer;
import com.victorprado.donus.core.usecase.performtransaction.PerformTransactionUseCase;
import com.victorprado.donus.core.usecase.performtransaction.SaveTransaction;
import com.victorprado.donus.core.usecase.performtransaction.UpdateBankAccountBalance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public CreateAccountUseCase createAccountUseCase(GetCustomer getCustomer, CreateBankAccount createBankAccount, GetBankAccount getBankAccount) {
        return new CreateAccountUseCase(getCustomer, createBankAccount, getBankAccount);
    }

    @Bean
    public PerformTransactionUseCase performMoneyTransfer(GetBankAccount getBankAccount, UpdateBankAccountBalance updateBankAccountBalance, SaveTransaction saveTransaction) {
        return new PerformTransactionUseCase(getBankAccount, updateBankAccountBalance, saveTransaction);
    }
}
