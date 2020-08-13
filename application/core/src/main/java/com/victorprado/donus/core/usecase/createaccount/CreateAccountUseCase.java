package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.condition.CheckNotNull;
import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateAccountUseCase {

    private static final String INVALID_CUSTOMER = "The customer is invalid";

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateAccountUseCase.class);

    private final ManageCustomer manageCustomer;
    private final ManageBankAccount manageBankAccount;

    public CreateAccountUseCase(ManageCustomer manageCustomer, ManageBankAccount manageBankAccount) {
        this.manageCustomer = manageCustomer;
        this.manageBankAccount = manageBankAccount;
    }

    public BankAccount create(Customer customer) throws InvalidEntityException {
        CheckNotNull.check(customer, INVALID_CUSTOMER);
        LOGGER.info("Starting new account creation for customer {}", customer.getName());
        LOGGER.info("validating new customer {}", customer.getName());
        customer.validate();
        try {
            LOGGER.info("searching for the customer {}", customer.getName());
            Customer customerEntity = manageCustomer.getOne(customer.getCpf())
                    .orElseThrow(CustomerNotFoundException::new);

            return createAccount(customerEntity);
        } catch (CustomerNotFoundException exception) {
            LOGGER.info("no customer were found with the CPF {}", customer.getCpf());
            return createAccountForNewCustomer(customer);
        }
    }

    private BankAccount createAccountForNewCustomer(Customer customer) {
        LOGGER.info("registering the new customer {}", customer.getName());
        manageCustomer.register(customer);
        return this.createAccount(customer);
    }

    private BankAccount createAccount(Customer customerEntity) {
        BankAccount account = new BankAccount.Builder()
                .customer(customerEntity)
                .build();

        LOGGER.info("creating new account. number {}", account.getNumber());
        manageBankAccount.create(account);
        return account;
    }
}
