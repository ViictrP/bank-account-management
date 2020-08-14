package com.victorprado.donus.core.usecase.createcustomer;

import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.exception.DataProviderException;

@FunctionalInterface
public interface CreateCustomer {

    void register(Customer customer) throws DataProviderException;
}
