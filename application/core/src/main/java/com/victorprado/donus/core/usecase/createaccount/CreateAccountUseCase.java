package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.condition.CheckNotNull;
import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateAccountUseCase {

    private static final String INVALID_CUSTOMER = "The customer is invalid";

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateAccountUseCase.class);

    private final GetCustomer getCustomer;
    private final CreateBankAccount createBankAccount;
    private final GetBankAccount getBankAccount;

    public CreateAccountUseCase(GetCustomer getCustomer, CreateBankAccount createBankAccount, GetBankAccount getBankAccount) {
        this.getCustomer = getCustomer;
        this.createBankAccount = createBankAccount;
        this.getBankAccount = getBankAccount;
    }

    public BankAccount create(Customer customer) throws InvalidEntityException, CustomerNotFoundException {
        CheckNotNull.check(customer, INVALID_CUSTOMER);
        LOGGER.info("validating customer request data {}", customer.getCpf());
        customer.validate();

        LOGGER.info("searching for the customer {}", customer.getCpf());
        Customer customerEntity = getCustomer.getOne(customer.getCpf())
                .orElseThrow(CustomerNotFoundException::new);

        LOGGER.info("validating if customer already has a bank account");
        boolean hasAccount = getBankAccount.getAccount(customerEntity.getId()).isPresent();

        if (hasAccount)
            throw new CustomerAlreadyHasAccountException();

        return createAccount(customerEntity);
    }

    private BankAccount createAccount(Customer customerEntity) {
        LOGGER.info("creating account for customer {}", customerEntity.getName());
        BankAccount account = new BankAccount.Builder()
                .customer(customerEntity)
                .build();

        createBankAccount.create(account);
        LOGGER.info("account created. number {}", account.getNumber());
        return account;
    }
}
