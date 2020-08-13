package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.entity.Customer;

import java.util.Optional;

public interface ManageCustomer {

    Optional<Customer> getOne(String cpf);
    void register(Customer customer) throws DataProviderException;
}
