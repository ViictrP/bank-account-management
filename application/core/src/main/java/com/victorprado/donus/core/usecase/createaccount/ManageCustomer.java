package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.entity.Customer;

import java.util.Optional;

public interface ManageCustomer {

    Optional<Customer> getOne(String cpf);
    boolean register(Customer customer) throws DataProviderException;
}
