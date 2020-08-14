package com.victorprado.donus.core.usecase.createcustomer;

import com.victorprado.donus.core.condition.CheckNull;
import com.victorprado.donus.core.condition.NotNullObject;
import com.victorprado.donus.core.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class CreateCustomerUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateCustomerUseCase.class);

    private final GetCustomer getCustomer;
    private final CreateCustomer createCustomer;
    private final UpdateCustomer updateCustomer;

    public CreateCustomerUseCase(GetCustomer getCustomer, CreateCustomer createCustomer, UpdateCustomer updateCustomer) {
        this.getCustomer = getCustomer;
        this.createCustomer = createCustomer;
        this.updateCustomer = updateCustomer;
    }

    public Customer create(Customer customer) {
        try {
            CheckNull.check(getCustomer.getOne(customer.getCpf()), "Customer already exist");

            return null;
        } catch (NotNullObject error) {
            LOGGER.error(error.getMessage());
            throw new CustomerAlreadyExist();
        }
    }
}
