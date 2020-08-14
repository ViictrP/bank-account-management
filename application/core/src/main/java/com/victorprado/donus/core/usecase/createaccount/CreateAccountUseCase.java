package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.condition.CheckNotNull;
import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.usecase.createcustomer.CustomerNotFoundException;
import com.victorprado.donus.core.usecase.createcustomer.GetCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateAccountUseCase {

    private static final String INVALID_CUSTOMER = "The customer is invalid";

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateAccountUseCase.class);

    private final GetCustomer getCustomer;
    private final CreateBankAccount createBankAccount;

    public CreateAccountUseCase(GetCustomer getCustomer, CreateBankAccount createBankAccount) {
        this.getCustomer = getCustomer;
        this.createBankAccount = createBankAccount;
    }

    public BankAccount create(Customer customer) throws InvalidEntityException {
        CheckNotNull.check(customer, INVALID_CUSTOMER);
        LOGGER.info("Starting new account creation for customer {}", customer.getName());
        LOGGER.info("validating new customer {}", customer.getName());
        customer.validate();
        LOGGER.info("searching for the customer {}", customer.getName());
        Customer customerEntity = getCustomer.getOne(customer.getCpf())
                .orElseThrow(CustomerNotFoundException::new);

        return createAccount(customerEntity);
    }

    private BankAccount createAccount(Customer customerEntity) {
        BankAccount account = new BankAccount.Builder()
                .customer(customerEntity)
                .build();

        LOGGER.info("creating new account. number {}", account.getNumber());
        createBankAccount.create(account);
        return account;
    }
}
